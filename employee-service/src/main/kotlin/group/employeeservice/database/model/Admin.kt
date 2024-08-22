package group.employeeservice.database.model

import jakarta.persistence.*

@Entity
@Table(name = "admin")
class Admin(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val admin_id: Long = 1,
    @OneToMany(mappedBy = "admin")
    var employees: List<Employee> = mutableListOf(),
)
