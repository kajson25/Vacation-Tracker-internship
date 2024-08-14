package group.adminservice.database.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import group.adminservice.security.api.jwt.Role
import jakarta.persistence.*

@Entity
@Table(name = "employee")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var employee_id: Long = 0,
    @Column(name = "email")
    var email: String = "",
    @Column(name = "password")
    var password: String = "",
    @ManyToOne
    @JoinColumn(name = "admin_id")
    @JsonBackReference
    val admin: Admin? = null,
    @OneToMany(mappedBy = "employee")
    @JsonManagedReference
    var usedDays: List<UsedDays> = mutableListOf(),
    @OneToMany(mappedBy = "employee")
    var vacations: List<Vacation> = mutableListOf(),
    @Enumerated(EnumType.STRING)
    val role: Role = Role.EMPLOYEE,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as Employee

        return email == other.email
    }

    override fun hashCode(): Int = email.hashCode()

    fun addVacation(vacation: Vacation): List<Vacation> = vacations.plus(vacation)

    fun addUsedDays(usedDay: UsedDays): List<UsedDays> = usedDays.plus(usedDay)

    fun calculateAllFreeDays(): Int {
        var res = 0
        for (vacation: Vacation in vacations) {
            res += vacation.noOfDays
        }
        return res
    }
}

