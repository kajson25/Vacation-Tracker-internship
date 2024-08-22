package group.employeeservice.database.model

import jakarta.persistence.*

@Entity
@Table(name = "vacation")
data class Vacation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val vacation_id: Long = 0,
    @Column(name = "noofdays", nullable = false)
    var noOfDays: Int = 0,
    @Column(name = "year", nullable = false)
    var year: Int = 2018,
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    var employee: Employee? = null,
)
