package group.adminservice.helper

import group.adminservice.database.model.Admin
import group.adminservice.database.model.Employee
import group.adminservice.database.model.UsedDays
import group.adminservice.database.model.Vacation
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.time.Instant
import java.util.*

object CSVParser {
    private fun readLines(data: ByteArray): List<String> {
        val inputStream = ByteArrayInputStream(data)
        val reader = BufferedReader(InputStreamReader(inputStream))
        return reader.lineSequence().drop(1).toList()
    }

    fun parseEmployees(
        data: ByteArray,
        admin: Admin,
    ) {
        val lines = readLines(data)
        var nextId = admin.getMaxId() + 1

        lines.forEach { line ->
            val parts = line.split(",")
            val employee = Employee(nextId++, parts[0], parts[1])
            admin.addEmployee(employee)
        }
    }

    fun parseVacations(
        data: ByteArray,
        admin: Admin,
    ): List<Vacation> {
        val vacations = mutableListOf<Vacation>()

        val lines = readLines(data)
        for (line in lines) {
            inner@ for (employee in admin.getAllEmployees()) {
                val parts = line.split(",")
                if (employee.email == parts[0]) {
                    val vacation = Vacation(noOfDays = parts[1].toInt(), employee = employee)
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

        val lines = readLines(data)
        for (line in lines) {
            inner@ for (employee in admin.getAllEmployees()) {
                val parts = line.split(",")
                if (employee.email == parts[0]) {
                    val usedDay =
                        UsedDays(
                            beginDay = parts[1],
                            beginDate = Date.from(Instant.parse(parts[2])),
                            endDay = parts[3],
                            endDate = Date.from(Instant.parse(parts[4])),
                            employee = employee,
                        )
                    employee.usedDays = employee.addUsedDays(usedDay)
                    usedDays.add(usedDay)
                    break@inner
                }
            }
        }
        return usedDays
    }
}
