package group.adminservice.repository

import group.adminservice.database.model.Vacation
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VacationRepository : CrudRepository<Vacation, Long> {
    fun findAllByEmployeeId(employeeId: Long): List<Vacation>
}

