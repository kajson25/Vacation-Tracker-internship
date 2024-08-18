package group.adminservice.service

import group.adminservice.database.model.Admin
import group.adminservice.dto.EmployeeDTO
import group.adminservice.dto.UsedDaysDTO
import group.adminservice.dto.VacationDTO

interface AdminService {
    fun getAllEmployees(): List<EmployeeDTO>

    fun importVacations(data: ByteArray): List<VacationDTO>

    fun importUsedDays(data: ByteArray): List<UsedDaysDTO>

    fun importEmployees(data: ByteArray): List<EmployeeDTO>

    fun getAdminById(id: Long): Admin // potencijalno ne mora da stoji u interfejsu, vec moze da bude privatna unutar klase
}
