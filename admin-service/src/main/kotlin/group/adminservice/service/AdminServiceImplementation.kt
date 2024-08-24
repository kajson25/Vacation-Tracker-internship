package group.adminservice.service

import group.adminservice.database.model.Admin
import group.adminservice.database.model.Employee
import group.adminservice.database.model.UsedDays
import group.adminservice.database.model.Vacation
import group.adminservice.dto.*
import group.adminservice.error.exceptions.BadRequestException
import group.adminservice.error.exceptions.ResourceNotFoundException
import group.adminservice.error.logger.logger
import group.adminservice.helper.*
import group.adminservice.repository.AdminRepository
import group.adminservice.repository.EmployeeRepository
import group.adminservice.repository.UsedDaysRepository
import group.adminservice.repository.VacationRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
@Transactional
class AdminServiceImplementation(
    private val employeeRepository: EmployeeRepository,
    private val vacationRepository: VacationRepository,
    private val usedDaysRepository: UsedDaysRepository,
    private val adminRepository: AdminRepository,
    private val mapper: Mapper,
    private val encoder: BCryptPasswordEncoder,
) : AdminService {
    val calculator: Calculator = Calculator()
    private val log = logger<AdminService>()

    override fun getAllEmployees(): List<EmployeeResponseDTO> {
        val employees = employeeRepository.findAll().toList()
        if (employees.isEmpty()) throw ResourceNotFoundException("No employees found")
        val res: List<EmployeeResponseDTO> =
            employees.map { employee ->
                mapper.mapEmployeeToEmployeeResponse(employee)
            }
        log.info("Fetched data from data base")
        return res
    }

    @Throws(ResourceNotFoundException::class, BadRequestException::class)
    override fun importVacations(data: ByteArray): List<VacationResponseDTO> {
        if (data.isEmpty()) {
            throw BadRequestException("CSV data cannot be empty")
        }
        val requestDto: List<VacationRequestDTO> = parseVacations(data)
        if (requestDto.isEmpty()) throw BadRequestException("CSV data cannot be empty")
        val vacationModel: List<Vacation> =
            requestDto.map { dto ->
                val emp = employeeRepository.findByEmail(dto.employeeEmail)
                if (!emp.isPresent) {
                    throw ResourceNotFoundException("Employee ${dto.employeeEmail} not found")
                }
                val employee = emp.get()
                val vacations = mapper.mapVacationRequestToVacation(dto, employee)
                employee.vacations = employee.vacations.plus(vacations)
                vacations
            }
        val vacations = vacationRepository.saveAll(vacationModel)
        log.info("Parsed and imported Vacations")
        val res: List<VacationResponseDTO> =
            vacations.map { vacation ->
                mapper.mapVacationToVacationResponse(vacation)
            }
        return res
    }

    @Throws(ResourceNotFoundException::class, BadRequestException::class)
    override fun importUsedDays(data: ByteArray): List<UsedDaysResponseDTO> {
        if (data.isEmpty()) {
            throw BadRequestException("CSV data cannot be empty")
        }
        val usedDaysDTO: List<UsedDaysRequestDTO> = parseUsedDays(data)
        if (usedDaysDTO.isEmpty()) {
            throw BadRequestException("CSV data cannot be empty")
        }
        val usedDaysModel: List<UsedDays> =
            usedDaysDTO.map { dto ->
                val emp = employeeRepository.findByEmail(dto.employeeEmail)
                if (!emp.isPresent) {
                    throw ResourceNotFoundException("Employee ${dto.employeeEmail} not found")
                }
                val employee = emp.get()
                val usedDays = mapper.mapUsedDaysRequestToUsedDays(dto, employee)
                employee.usedDays = employee.usedDays.plus(usedDays)
                usedDays
            }

        log.info("Finished parsing CSV data")
        lowerVacation(usedDaysModel)
        usedDaysRepository.saveAll(usedDaysModel)
        log.info("Lowered used days")
        val usedDaysToSave: List<UsedDaysResponseDTO> =
            usedDaysModel.map { dto ->
                mapper.mapUsedDaysToUsedDaysResponse(dto)!!
            }

        return usedDaysToSave
    }

    @Throws(ResourceNotFoundException::class, BadRequestException::class)
    override fun importEmployees(data: ByteArray): List<EmployeeResponseDTO> {
        if (data.isEmpty()) {
            throw BadRequestException("CSV data cannot be empty")
        }
        val admin = getAdminById(1)
        val employeeRequest: List<EmployeeRequestDTO> = parseEmployees(data)
        val employeeModel: List<Employee> =
            employeeRequest.map { dto ->
                dto.password = encoder.encode(dto.password)
                mapper.mapEmployeeRequestToEmployee(dto, admin)
            }
        val employees = employeeRepository.saveAll(employeeModel)
        admin.employees = admin.employees.plus(employeeModel)
        log.info("Parsed and imported employee")
        val res: List<EmployeeResponseDTO> =
            employees.map { employee ->
                mapper.mapEmployeeToEmployeeResponse(employee)
            }
        return res
    }

    // staviti da bude extension
    private fun getAdminById(id: Long): Admin {
        val adminO = adminRepository.findById(id)
        return adminO.orElseThrow { ResourceNotFoundException("Admin not found") }
    }

    @Throws(ResourceNotFoundException::class)
    fun lowerVacation(usedDays: List<UsedDays>) {
        var res: List<Vacation> = mutableListOf()
        for (usedDay: UsedDays in usedDays) {
            var i = 0

            // Fetch the employee with vacations from the database
            val employee =
                usedDay.employee?.let {
                    employeeRepository.findById(it.employee_id).orElseThrow {
                        ResourceNotFoundException("Employee not found")
                    }
                }
            if (employee == null) {
                continue
            }

            var workDaysLeft =
                usedDay.beginDate?.let {
                    usedDay.endDate?.let { it1 ->
                        calculator.calculateWorkDays(
                            beginDate = it,
                            endDate = it1,
                        )
                    }
                }

            val year = usedDay.beginDate?.year
            val vacations = employee.vacations
            if (workDaysLeft == null || year?.let { calculator.calculateAllFreeDays(employee, it) }!! < workDaysLeft) {
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

        vacationRepository.saveAll(res)
    }
}
