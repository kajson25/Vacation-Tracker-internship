package group.adminservice.database.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import lombok.NoArgsConstructor

@Entity
@Table(name = "vacation")
@NoArgsConstructor
data class Vacation(
    @Id
    val vacation_id: Long = 0,
    @Column(name = "noofdays")
    var noOfDays: Int = 0,
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    val employee: Employee? = null,
)
