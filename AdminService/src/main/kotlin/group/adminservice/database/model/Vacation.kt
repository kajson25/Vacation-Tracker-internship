package group.adminservice.database.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "vacation")
data class Vacation(
    @Id
    val vacation_id: Long = 0,
    @Column(name = "noofdays")
    var noOfDays: Int = 0,
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    val employee: Employee? = null,
) {
    constructor(vacation_id: Long, noOfDays: Int) : this()
}
