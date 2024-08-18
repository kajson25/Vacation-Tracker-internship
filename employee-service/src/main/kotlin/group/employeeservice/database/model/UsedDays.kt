package group.employeeservice.database.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.sql.Date
import java.time.DayOfWeek

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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as UsedDays

        // Consider two UsedDays equal if either beginDate or endDate are the same
        return (beginDate == other.beginDate || endDate == other.endDate)
    }

    override fun hashCode(): Int {
        // XOR combination to create a hash code considering both dates
        return (beginDate?.hashCode() ?: 0) xor (endDate?.hashCode() ?: 0)
    }
}
