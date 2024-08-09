package group.adminservice.helper

import group.adminservice.database.model.Admin
import group.adminservice.database.model.Employee
import group.adminservice.database.model.UsedDays
import group.adminservice.database.model.Vacation
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.sql.Date
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object CSVParser {
    private fun readLines(
        data: ByteArray,
        firstRow: Boolean,
    ): List<String> {
        val inputStream = ByteArrayInputStream(data)
        val reader = BufferedReader(InputStreamReader(inputStream))
        if (firstRow) {
            return reader.readLines()
        }
        return reader.lineSequence().drop(1).toList()
    }

    fun parseEmployees(
        data: ByteArray,
        admin: Admin,
    ): List<Employee> {
        val res = mutableListOf<Employee>()
        val lines = readLines(data, false)
        var nextId = admin.getMaxId() + 1

        for (line: String in lines) {
            val parts = line.split(",")
            val employee = Employee(nextId++, parts[0], parts[1])
            if (admin.getAllEmployees().contains(employee)) {
                continue
            }
            admin.addEmployee(employee)
            res.add(employee)
        }
        return res
    }

    fun parseVacations(
        data: ByteArray,
        admin: Admin,
    ): List<Vacation> {
        val vacations = mutableListOf<Vacation>()

        val lines = readLines(data, true)
        val year = lines[0].split(",")[1].toInt()
        for (line in lines) {
            inner@ for (employee in admin.getAllEmployees()) {
                val parts = line.split(",")
                if (employee.email == parts[0]) {
                    val vacation = Vacation(noOfDays = parts[1].toInt(), year = year, employee = employee)
                    employee.vacations = employee.addVacation(vacation)
                    vacations.add(vacation)
                    break@inner
                }
            }
        }
        return vacations
    }

    fun parseUsedDays(
        data: ByteArray,
        admin: Admin,
    ): List<UsedDays> {
        val usedDays = mutableListOf<UsedDays>()

        val lines = readLines(data, false)
        for (line in lines) {
            inner@ for (employee in admin.getAllEmployees()) {
                val emails = line.split(",")
                if (employee.email == emails[0]) {
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
                    if(employee.usedDays.contains(usedDay)) {
                        break@inner
                    }
                    employee.usedDays = employee.addUsedDays(usedDay)
                    usedDays.add(usedDay)
                    break@inner
                }
            }
        }
        return usedDays
    }

    fun calculateWorkDays(
        beginDate: Date?,
        endDate: Date?,
    ): Int {
        var workingDays = 0
        var currentDate = beginDate?.toLocalDate()

        if (endDate != null && currentDate != null) {
            while (currentDate!! <= endDate.toLocalDate()) {
                if (currentDate.dayOfWeek != DayOfWeek.SATURDAY && currentDate.dayOfWeek != DayOfWeek.SUNDAY) {
                    workingDays++
                }
                currentDate = currentDate.plusDays(1)
            }
        }

        return workingDays
    }
}
