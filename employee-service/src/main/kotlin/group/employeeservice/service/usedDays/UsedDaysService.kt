package group.employeeservice.service.usedDays

import group.employeeservice.dto.UsedDaysDTO

interface UsedDaysService {
    fun addUsedDay(
        data: ByteArray,
        token: String,
    ): UsedDaysDTO?
}
