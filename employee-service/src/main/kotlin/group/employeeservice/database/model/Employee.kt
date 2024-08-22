package group.employeeservice.database.model

import jakarta.persistence.*

@Entity
@Table(name = "employee")
class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val employee_id: Long = 0,
    @Column(name = "email", unique = true)
    var email: String = "",
    @Column(name = "password", nullable = false)
    var password: String = "",
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    var admin: Admin? = null,
    @OneToMany(mappedBy = "employee")
    var usedDays: List<UsedDays> = mutableListOf(),
    @OneToMany(mappedBy = "employee")
    var vacations: List<Vacation> = mutableListOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as Employee

        return email == other.email
    }

    override fun hashCode(): Int = email.hashCode()
}
