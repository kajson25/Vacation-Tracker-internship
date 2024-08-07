package group.adminservice.service

import group.adminservice.database.model.Admin
import group.adminservice.database.model.Employee
import group.adminservice.database.model.UsedDays
import group.adminservice.database.model.Vacation
import group.adminservice.helper.CSVParser
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
        TODO("Not yet implemented")
    }

    override fun importUsedDays(data: ByteArray): List<UsedDays> {
        TODO("Not yet implemented")
    }

    override fun importEmployees(data: ByteArray): List<Employee> {
        println("zovem repo")
        val admin = getAdminById(1)
        CSVParser.parseEmployees(data, admin)
        return employeeRepository.saveAll(admin.getAllEmployees())
    }

    override fun getAdminById(id: Long): Admin {
        println("Trazim admina")
        val adminO = adminRepository.findById(id)
        return adminO.orElseThrow { RuntimeException("Admin not found") }
    }
}

data class EmployeeDetails(
    val employee: Employee,
    val vacations: List<Vacation>,
    val usedDays: List<UsedDays>,
)

