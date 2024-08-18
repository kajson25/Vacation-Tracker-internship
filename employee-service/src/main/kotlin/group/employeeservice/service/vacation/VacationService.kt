package group.employeeservice.service.vacation

interface VacationService {
    fun getUsedDays(
        token: String,
        year: Int,
    ): Int

    fun getAllVacationDays(
        token: String,
        year: Int,
    ): Int

    fun getAvailableDays(
        token: String,
        year: Int,
    ): Int

    fun getUsedDaysInPeriod(
        token: String,
        startDate: String,
        endDate: String,
    ): Int
}
