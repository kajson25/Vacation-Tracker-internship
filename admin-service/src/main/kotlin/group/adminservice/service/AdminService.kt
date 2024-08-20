package group.adminservice.service

import group.adminservice.dto.EmployeeDTO
import group.adminservice.dto.UsedDaysDTO
import group.adminservice.dto.VacationDTO

interface AdminService {
    fun getAllEmployees(): List<EmployeeDTO>

    fun importVacations(data: ByteArray): List<VacationDTO>

    fun importUsedDays(data: ByteArray): List<UsedDaysDTO>

    fun importEmployees(data: ByteArray): List<EmployeeDTO>
}
