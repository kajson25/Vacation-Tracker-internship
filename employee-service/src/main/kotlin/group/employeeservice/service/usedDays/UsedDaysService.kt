package group.employeeservice.service.usedDays

import group.employeeservice.dto.UsedDaysResponseDTO

interface UsedDaysService {
    fun addUsedDay(
        data: ByteArray,
        token: String,
    ): UsedDaysResponseDTO?
}
