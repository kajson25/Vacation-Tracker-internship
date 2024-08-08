package group.adminservice.database.model

import com.fasterxml.jackson.annotation.JsonBackReference
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
    var usedDays: List<UsedDays> = mutableListOf(),
    @OneToMany(mappedBy = "employee")
    var vacations: List<Vacation> = mutableListOf(),
) {
    constructor(id: Long, email: String, password: String) : this(id, email, password, null, emptyList())

    fun addVacation(vacation: Vacation): List<Vacation> = vacations.plus(vacation)

    fun getAllVacations(): List<Vacation> = vacations

    fun getMaxVacationId(): Long = vacations.maxOfOrNull { it.vacation_id } ?: 0

    fun addUsedDays(usedDay: UsedDays): List<UsedDays> = usedDays.plus(usedDay)

    fun getAllUsedDays(): List<UsedDays> = usedDays

    fun getMaxUsedDaysId(): Long = usedDays.maxOfOrNull { it.useddays_id } ?: 0
}

