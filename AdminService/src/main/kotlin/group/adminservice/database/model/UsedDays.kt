@file:Suppress("ktlint:standard:no-wildcard-imports")

package group.adminservice.database.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("UsedDays")
data class UsedDays(
    @Id
    val id: Long = 0,
    @Column("beginday")
    val beginDay: String,
    @Column("begindate")
    val beginDate: Date,
    @Column("endday")
    val endDay: String,
    @Column("enddate")
    val endDate: Date,
    @Column("employeeid")
    val employeeId: Long,
)
