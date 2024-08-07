package group.adminservice.helper

import group.adminservice.database.model.Admin
import group.adminservice.database.model.Employee
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

object CSVParser {
    private fun readLines(
        data: ByteArray,
        admin: Admin,
    ): List<String> {
        val inputStream = ByteArrayInputStream(data)
        val reader = BufferedReader(InputStreamReader(inputStream))

        return reader.lineSequence().drop(1).toList()
    }

    fun parseEmployees(
        data: ByteArray,
        admin: Admin,
    ) {
        val lines = readLines(data, admin)
        var nextId = admin.getMaxId() + 1

        lines.forEach { line ->
            val parts = line.split(",")
            val employee = Employee(nextId++, parts[0], parts[1])
            admin.addEmployee(employee)
        }
    }
}
