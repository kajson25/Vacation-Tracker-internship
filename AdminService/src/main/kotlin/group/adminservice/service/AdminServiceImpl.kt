package group.adminservice.service

import group.adminservice.database.model.Admin
import group.adminservice.database.model.Employee
import group.adminservice.database.model.UsedDays
import group.adminservice.database.model.Vacation
import group.adminservice.helper.CSVParser
import group.adminservice.helper.CSVParser.calculateWorkDays
import group.adminservice.repository.AdminRepository
import group.adminservice.repository.EmployeeRepository
import group.adminservice.repository.UsedDaysRepository
import group.adminservice.repository.VacationRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class AdminServiceImpl(
    private val employeeRepository: EmployeeRepository,
    private val vacationRepository: VacationRepository,
    private val usedDaysRepository: UsedDaysRepository,
    private val adminRepository: AdminRepository,
) : AdminService {
    override fun getAllEmployees(): List<Employee> = employeeRepository.findAll().toList()

    override fun importVacations(data: ByteArray): List<Vacation> {
        val admin = getAdminById(1)
        return vacationRepository.saveAll(
            CSVParser.parseVacations(data, admin)
        )
    }

    override fun importUsedDays(data: ByteArray): List<UsedDays> {
        val admin = getAdminById(1)
        val usedDaysToSave = CSVParser.parseUsedDays(data, admin)
        lowerVacation(usedDaysToSave)
        return usedDaysRepository.saveAll(usedDaysToSave)
    }

    override fun importEmployees(data: ByteArray): List<Employee> {
        val admin = getAdminById(1)
        return employeeRepository.saveAll(CSVParser.parseEmployees(data, admin))
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

            var workDaysLeft = calculateWorkDays(beginDate = usedDay.beginDate, endDate = usedDay.endDate)

            val vacations = employee.vacations
            if (employee.calculateAllFreeDays() < workDaysLeft) {
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
