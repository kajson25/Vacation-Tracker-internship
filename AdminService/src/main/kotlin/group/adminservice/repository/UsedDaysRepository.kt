package group.adminservice.repository

import group.adminservice.database.model.UsedDays
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsedDaysRepository : CrudRepository<UsedDays, Long> {
    fun findAllByEmployeeId(employeeId: Long): List<UsedDays>
}
