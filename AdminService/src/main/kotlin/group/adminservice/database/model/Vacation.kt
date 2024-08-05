package group.adminservice.database.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("Vacation")
data class Vacation(
    @Id
    val id: Long = 0,
    @Column("noofdays")
    var noOfDays: Int,
    @Column("employeeid")
    var employeeId: Long,
)
