package group.adminservice.database.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("Admin")
data class Admin(
    @Id
    val id: Long,
)

