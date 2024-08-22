package group.adminservice.service

import group.adminservice.dto.EmployeeResponseDTO
import group.adminservice.dto.UsedDaysResponseDTO
import group.adminservice.dto.VacationResponseDTO

interface AdminService {
    fun getAllEmployees(): List<EmployeeResponseDTO>

    fun importVacations(data: ByteArray): List<VacationResponseDTO>

    fun importUsedDays(data: ByteArray): List<UsedDaysResponseDTO>

    fun importEmployees(data: ByteArray): List<EmployeeResponseDTO>
}
