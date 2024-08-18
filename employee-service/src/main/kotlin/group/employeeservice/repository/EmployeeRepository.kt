package group.employeeservice.repository

import group.employeeservice.database.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {
    fun findByEmail(email: String): Optional<Employee>
}
