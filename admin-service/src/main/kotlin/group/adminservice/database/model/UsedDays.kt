@file:Suppress("ktlint:standard:no-wildcard-imports")

package group.adminservice.database.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "useddays")
class UsedDays(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val useddays_id: Long = 0,
    @Column(name = "begindate", nullable = false)
    var beginDate: LocalDate? = null,
    @Column(name = "enddate", nullable = false)
    var endDate: LocalDate? = null,
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    var employee: Employee? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as UsedDays

        // Two UsedDays are equal if either beginDate or endDate are the same
        return (beginDate == other.beginDate || endDate == other.endDate)
    }

    override fun hashCode(): Int {
        // XOR combination to create a hash code considering both dates
        return (beginDate?.hashCode() ?: 0) xor (endDate?.hashCode() ?: 0)
    }
}
