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
    @Column("beginDay")
    val beginDay: String,
    @Column("beginDate")
    val beginDate: Date,
    @Column("endDay")
    val endDay: String,
    @Column("endDate")
    val endDate: Date,
    @Column("employeeId")
    val employeeId: Long,
)
