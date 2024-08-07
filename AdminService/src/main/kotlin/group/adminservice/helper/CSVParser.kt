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
        val vacations: List<Vacation> = listOf()
        readLines(data).forEach {
            admin.getAllEmployees().forEach { employee ->
                // cim nadjes employee za mail, break
                val parts = it.split(",")
                var nextId = employee.getMaxVacationId() + 1
                if (employee.email == parts[0]) {
                    val vacation = Vacation(nextId++, parts[1].toInt())
                    employee.addVacation(vacation)
                    vacations.plus(vacation)
                    // break; todo ?????????? optimizacija brooo
                }
            }
        }
        // za slucaj da je admin null
//        employee.admin?.let { readLines(data, it) }?.forEach { line ->
//            val parts = line.split(",")
//
//            // todo nije dobra logika; ja za jednog employee-ja dodajem sve Vacatione
//            // a treba samo ako se slazu mejlovi, razmisli
//        }
        return vacations
    }

    fun parseUsedDays(
        data: ByteArray,
        admin: Admin,
    ): List<UsedDays> {
        val usedDays: List<UsedDays> = listOf()
        readLines(data).forEach {
            admin.getAllEmployees().forEach { employee ->
                // cim nadjes employee za mail, break
                val parts = it.split(",")
                var nextId = employee.getMaxUsedDaysId() + 1
                if (employee.email == parts[0]) {
                    val usedDay = UsedDays(nextId++, parts[1],
                                            Date.from(Instant.parse(parts[2])),
                                            parts[3],
                                            Date.from(Instant.parse(parts[4]))
                                        )
                    employee.addUsedDays(usedDay)
                    usedDays.plus(usedDay)
                    // break; todo ?????????? optimizacija brooo
                }
            }
        }
        return usedDays
    }
}
