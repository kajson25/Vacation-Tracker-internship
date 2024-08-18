package group.employeeservice.service.employee

import group.employeeservice.dto.EmployeeResponseDTO

interface EmployeeService {
    fun findByEmail(email: String): EmployeeResponseDTO
}
