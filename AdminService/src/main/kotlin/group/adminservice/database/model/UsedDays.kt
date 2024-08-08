package group.adminservice.database.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "useddays")
data class UsedDays(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val useddays_id: Long = 0,
    @Column(name = "beginday")
    val beginDay: String = "",
    @Column(name = "begindate")
    val beginDate: Date? = null,
    @Column(name = "endday")
    val endDay: String = "",
    @Column(name = "enddate")
    val endDate: Date? = null,
    @ManyToOne
    @JoinColumn(name = "employee_id")
    val employee: Employee? = null,
)
