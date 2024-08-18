package group.employeeservice.helper

import group.employeeservice.database.model.Employee
import group.employeeservice.database.model.UsedDays
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.sql.Date
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Parser {
    private fun readLine(data: ByteArray): String {
        val inputStream = ByteArrayInputStream(data)
        val reader = BufferedReader(InputStreamReader(inputStream))
        return reader.readLines().joinToString("\n")
    }

    fun parseUsedDays(
        data: ByteArray,
        employee: Employee,
    ): UsedDays? {
        val line = readLine(data)
        val regex = """"([^"]*)"""".toRegex() // Regex to match quoted strings
        val parts = regex.findAll(line).toList()
        val beginDate = parts[0].groupValues[1]
        val endDate = parts[1].groupValues[1]
        val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")
        val usedDay =
            UsedDays(
                beginDate = Date.valueOf(LocalDate.parse(beginDate, formatter)),
                endDate = Date.valueOf(LocalDate.parse(endDate, formatter)),
                employee = employee,
            )
        if (employee.usedDays.contains(usedDay)) {
            return null
        }
        employee.usedDays = employee.addUsedDays(usedDay)
        return usedDay
    }
}
