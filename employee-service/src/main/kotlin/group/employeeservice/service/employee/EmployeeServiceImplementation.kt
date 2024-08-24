package group.employeeservice.service.employee

import group.employeeservice.dto.EmployeeResponseDTO
import group.employeeservice.dto.Mapper
import group.employeeservice.error.exception.ResourceNotFoundException
import group.employeeservice.error.logger.logger
import group.employeeservice.repository.EmployeeRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class EmployeeServiceImplementation(
    val employeeRepository: EmployeeRepository,
    val mapper: Mapper,
) : EmployeeService {
    private val log = logger<EmployeeService>()

    override fun findByEmail(email: String): EmployeeResponseDTO {
        val employee =
            employeeRepository.findByEmail(email)
                ?: throw ResourceNotFoundException("No employee with the email: $email")
        log.info("Found an employee in data base: ${employee.email}")
        return mapper.mapEmployeeToEmployeeResponse(employee)
    }
}
