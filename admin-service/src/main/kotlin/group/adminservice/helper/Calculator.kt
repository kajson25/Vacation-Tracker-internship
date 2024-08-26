package group.adminservice.helper

import group.adminservice.database.model.Employee
import group.adminservice.database.model.Vacation
import java.time.DayOfWeek
import java.time.LocalDate

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

fun Employee.calculateAllFreeDays(year: Int): Int {
    var res = 0
    for (vacation: Vacation in this.vacations) {
        if (vacation.year == year) {
            res += vacation.noOfDays
        }
    }
    return res
}
