package group.adminservice.service

import group.adminservice.database.model.Employee
import group.adminservice.database.model.UsedDays
import group.adminservice.database.model.Vacation
import group.adminservice.repository.EmployeeRepository
import group.adminservice.repository.UsedDaysRepository
import group.adminservice.repository.VacationRepository
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val employeeRepository: EmployeeRepository,
    private val vacationRepository: VacationRepository,
    private val usedDaysRepository: UsedDaysRepository
) {
    fun getAllEmployees(): List<Employee> {
        return employeeRepository.findAll().toList()
    }

    fun getEmployeeWithDetails(employeeId: Long): EmployeeDetails {
        val employee = employeeRepository.findById(employeeId).orElseThrow { RuntimeException("Employee not found") }
        val vacations = vacationRepository.findAllByEmployeeId(employeeId)
        val usedDays = vacations.flatMap { vacation -> usedDaysRepository.findAllByEmployeeId(vacation.id) }
        return EmployeeDetails(employee, vacations, usedDays)
    }
}

data class EmployeeDetails(
    val employee: Employee,
    val vacations: List<Vacation>,
    val usedDays: List<UsedDays>
)

