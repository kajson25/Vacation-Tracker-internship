package group.adminservice.database.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.sql.Date

@Entity
@Table(name = "useddays")
data class UsedDays(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val useddays_id: Long = 0,
    @Column(name = "begindate")
    val beginDate: Date? = null,
    @Column(name = "enddate")
    val endDate: Date? = null,
    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    val employee: Employee? = null,
)
