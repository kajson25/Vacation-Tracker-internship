package group.employeeservice.helper

import group.employeeservice.database.model.Employee
import group.employeeservice.database.model.UsedDays
import group.employeeservice.database.model.Vacation
import java.time.DayOfWeek
import java.time.LocalDate

private fun calculateWorkDays( // funkcija ne zavisi od usedDays ili meployee id
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

fun Employee.calculateAvailableDays(year: Int): Int {
    var res = 0
    for (vacation: Vacation in this.vacations) {
        if (vacation.year == year) {
            res += vacation.noOfDays
        }
    }
    return res
}

fun Employee.calculateAllUsedDays(
    beginDate: LocalDate,
    endDate: LocalDate,
): Int {
    var res = 0
    val year = beginDate.year
    val tempDay = UsedDays(beginDate = beginDate, endDate = endDate, employee = this)

    if (this.usedDays.contains(tempDay)) {
        if (tempDay.beginDate == null || tempDay.endDate == null) {
            return -1
        }
        return calculateWorkDays(tempDay.beginDate!!, tempDay.endDate!!)
    }

    for (usedDay: UsedDays in this.usedDays) {
        if (usedDay.beginDate == null || usedDay.endDate == null) {
            continue
        }
        if (usedDay.beginDate!!.year != year) {
            continue
        }
        res += calculateWorkDays(usedDay.beginDate!!, usedDay.endDate!!)
    }
    return res
}

fun Employee.calculateAllDays(
    startDate: LocalDate,
    endDate: LocalDate,
): Int =
    this.calculateAllUsedDays(startDate, endDate) +
        this.calculateAvailableDays(startDate.year)
