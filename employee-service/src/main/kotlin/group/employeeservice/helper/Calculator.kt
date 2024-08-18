package group.employeeservice.helper

import group.employeeservice.database.model.Employee
import group.employeeservice.database.model.UsedDays
import group.employeeservice.database.model.Vacation
import java.sql.Date
import java.time.DayOfWeek
import java.time.LocalDate

class Calculator {
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

    fun calculateAvailableDays(
        employee: Employee,
        year: Int,
    ): Int {
        var res = 0
        for (vacation: Vacation in employee.vacations) {
            if (vacation.year == year) {
                res += vacation.noOfDays
            }
        }
        return res
    }

    // todo - sql date in line 49
    fun calculateAllUsedDays(
        employee: Employee,
        beginDate: LocalDate,
        endDate: LocalDate,
    ): Int {
        var res = 0
        val year = beginDate.year
        val tempDay = UsedDays(beginDate = Date.valueOf(beginDate), endDate = Date.valueOf(endDate), employee = employee)
        if (employee.usedDays.contains(tempDay)) {
            if (tempDay.beginDate == null || tempDay.endDate == null) {
                return -1
            }
            return calculateWorkDays(tempDay.beginDate.toLocalDate(), tempDay.endDate.toLocalDate())
        }
        for (usedDay: UsedDays in employee.usedDays) {
            if (usedDay.beginDate == null || usedDay.endDate == null) {
                continue
            }
            if (usedDay.beginDate.toLocalDate().year != year) {
                continue
            }
            res += calculateWorkDays(usedDay.beginDate.toLocalDate(), usedDay.endDate.toLocalDate())
        }
        return res
    }

    fun calculateAllDays(
        employee: Employee,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Int =
        calculateAllUsedDays(employee, startDate, endDate) +
            calculateAvailableDays(employee, startDate.year)
}
