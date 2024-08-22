package group.adminservice.repository

import group.adminservice.database.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long>{
    @Query("SELECT e FROM Employee e WHERE e.email = :email")
    fun findByEmail(
        @Param("email") email: String,
    ): Optional<Employee>
}
