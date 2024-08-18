package group.adminservice.helper

import java.time.DayOfWeek
import java.time.LocalDate

class Calculator {
    fun calculateWorkDays(
        beginDate: LocalDate,
        endDate: LocalDate,
    ): Int {
        var workingDays = 0
        var currentDate = beginDate

        while (currentDate <= endDate) {
            if (currentDate.dayOfWeek != DayOfWeek.SATURDAY && currentDate.dayOfWeek != DayOfWeek.SUNDAY) {
                workingDays++
            }
            currentDate = currentDate.plusDays(1)
        }

        return workingDays
    }
}
