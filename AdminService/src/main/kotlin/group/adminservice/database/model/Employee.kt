package group.adminservice.database.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("Employee")
data class Employee(
    @Id
    val id: Long = 0,
    @Column("email")
    var email: String,
    @Column("password")
    var password: String,
    @Transient
    var vacations: List<Vacation> = listOf(),
    @Transient
    var usedDays: List<UsedDays> = listOf(),
    @Column("adminId")
    val adminId: Long,
)
