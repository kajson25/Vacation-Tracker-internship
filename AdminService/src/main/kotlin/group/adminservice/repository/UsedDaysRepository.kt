package group.adminservice.repository

import group.adminservice.database.model.UsedDays
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsedDaysRepository : JpaRepository<UsedDays, Long>
