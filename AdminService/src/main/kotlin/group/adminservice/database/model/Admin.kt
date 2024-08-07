package group.adminservice.database.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@Entity
@Table(name = "admin")
@NoArgsConstructor
@AllArgsConstructor
data class Admin(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var admin_id: Long = 0,
    @OneToMany(mappedBy = "admin")
    @JsonManagedReference
    var employees: List<Employee> = listOf(),
) {
    fun addEmployee(employee: Employee) {
        employees.plus(employee)
    }

    fun getAllEmployees(): List<Employee> = employees

    fun getMaxId(): Long = employees.maxOfOrNull { it.employee_id } ?: 0
}

