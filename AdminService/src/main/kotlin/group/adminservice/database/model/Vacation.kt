package group.adminservice.database.model

import jakarta.persistence.*

@Entity
@Table(name = "vacation")
data class Vacation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val vacation_id: Long = 0,
    @Column(name = "noofdays")
    var noOfDays: Int = 0,
    @ManyToOne
    @JoinColumn(name = "employee_id")
    var employee: Employee? = null,
)
