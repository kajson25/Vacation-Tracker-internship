package group.adminservice.service

import group.adminservice.database.model.Admin
import group.adminservice.database.model.Employee
import group.adminservice.database.model.UsedDays
import group.adminservice.database.model.Vacation
import group.adminservice.dto.EmployeeDTO
import group.adminservice.dto.Mapper
import group.adminservice.dto.UsedDaysDTO
import group.adminservice.dto.VacationDTO
import group.adminservice.helper.CSVParser
import group.adminservice.helper.Calculator
import group.adminservice.repository.AdminRepository
import group.adminservice.repository.EmployeeRepository
import group.adminservice.repository.UsedDaysRepository
import group.adminservice.repository.VacationRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class AdminServiceImplementation(
    private val employeeRepository: EmployeeRepository,
    private val vacationRepository: VacationRepository,
    private val usedDaysRepository: UsedDaysRepository,
    private val adminRepository: AdminRepository,
    private val mapper: Mapper,
) : AdminService {
    val parser : CSVParser = CSVParser()
    val calculator : Calculator = Calculator()

    override fun getAllEmployees(): List<EmployeeDTO> {
        val employees = employeeRepository.findAll().toList()
        val res: List<EmployeeDTO> =
            employees.map { employee ->
                // println("Mapiram employeeja ${employee.email}")
                mapper.mapEmployee(employee)
            }
        return res
    }

    override fun importVacations(data: ByteArray): List<VacationDTO> {
        val admin = getAdminById(1)
        val vacations = vacationRepository.saveAll(parser.parseVacations(data, admin))
        val res: List<VacationDTO> =
            vacations.map { vacation ->
                mapper.mapVacation(vacation)!!
            }
        return res
    }

    override fun importUsedDays(data: ByteArray): List<UsedDaysDTO> {
        val admin = getAdminById(1)
        val usedDaysToSave = parser.parseUsedDays(data, admin)
        lowerVacation(usedDaysToSave)
        val temp = usedDaysRepository.saveAll(usedDaysToSave)
        val res: List<UsedDaysDTO> =
            temp.map { usedDays ->
                mapper.mapUsedDays(usedDays)!!
            }
        return res
    }

    override fun importEmployees(data: ByteArray): List<EmployeeDTO> {
        val admin = getAdminById(1)
        val employees = employeeRepository.saveAll(parser.parseEmployees(data, admin))
        val res: List<EmployeeDTO> =
            employees.map { employee ->
                mapper.mapEmployee(employee)
            }
        return res
    }

    // ne izdvajati u funkciju
    override fun getAdminById(id: Long): Admin {
        val adminO = adminRepository.findById(id)
        return adminO.orElseThrow { RuntimeException("Admin not found") }
    }

    fun getEmployeeByMail(email: String): Employee = employeeRepository.findByEmail(email).orElseThrow()

    fun lowerVacation(usedDays: List<UsedDays>) {
        var res: List<Vacation> = mutableListOf()
        for (usedDay: UsedDays in usedDays) {
            var i = 0

            // Fetch the employee with vacations from the database
            val employee =
                usedDay.employee?.let {
                    employeeRepository.findById(it.employee_id).orElseThrow {
                        IllegalArgumentException("Employee not found")
                    }
                }
            if (employee == null) {
                continue
            }

            var workDaysLeft =
                usedDay.beginDate?.let { usedDay.endDate?.let { it1 -> calculator.calculateWorkDays(beginDate = it.toLocalDate(), endDate = it1.toLocalDate()) } }

            val vacations = employee.vacations
            // todo ispraviti uslov
            if (workDaysLeft == null || employee.calculateAllFreeDays() < workDaysLeft) {
                throw RuntimeException("You do not have that many vacation days! You only have ${employee.vacations.size}")
            }

            while (workDaysLeft > 0) {
                if (i == vacations.size) {
                    i = 0
                }
                val vacation = vacations[i]
                if (vacation.noOfDays > 0) {
                    vacation.noOfDays -= 1
                    workDaysLeft--
                }
                i++
            }
            res = vacations
        }

        // Save updated vacations to the database
        vacationRepository.saveAll(res)
    }
}
