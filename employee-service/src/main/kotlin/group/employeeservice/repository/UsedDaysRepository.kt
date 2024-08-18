package group.employeeservice.repository

import group.employeeservice.database.model.UsedDays
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsedDaysRepository : JpaRepository<UsedDays, Long>
