package group.adminservice.repository

import group.adminservice.database.model.Vacation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VacationRepository : JpaRepository<Vacation, Long>

