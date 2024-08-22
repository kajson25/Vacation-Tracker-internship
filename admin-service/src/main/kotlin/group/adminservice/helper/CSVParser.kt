package group.adminservice.helper

import group.adminservice.dto.EmployeeRequestDTO
import group.adminservice.dto.UsedDaysRequestDTO
import group.adminservice.dto.VacationRequestDTO
import group.adminservice.error.exceptions.UnsupportedMediaTypeException
import group.adminservice.error.exceptions.ValidationException
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

// Validation functions
fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)\$"
    return Pattern.compile(emailRegex).matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    return password.isNotBlank()
}

fun isValidYear(year: Int): Boolean = year in 1970..2030

fun parseEmployees(data: ByteArray): List<EmployeeRequestDTO> {
    val res = mutableListOf<EmployeeRequestDTO>()
    val inputStream = ByteArrayInputStream(data)
    val reader = InputStreamReader(inputStream)
    val parser = CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())

    for (record in parser) {
        val email = record.get(0)
        val password = record.get(1)

        if (!isValidEmail(email)) {
            throw ValidationException("Invalid email format: $email")
        }

        if (!isValidPassword(password)) {
            throw ValidationException("Invalid password")
        }

        res.add(EmployeeRequestDTO(email = email, password = password))
    }
    return res
}

fun parseVacations(data: ByteArray): List<VacationRequestDTO> {
    val vacations = mutableListOf<VacationRequestDTO>()
    val inputStream = ByteArrayInputStream(data)
    val reader = InputStreamReader(inputStream)
    val lines = reader.readLines()

    // Extract the year from the first line
    val yearLine = lines[0]
    val year = yearLine.split(",")[1].toIntOrNull() ?: throw UnsupportedMediaTypeException("Invalid year format in CSV")

    if (!isValidYear(year)) {
        throw ValidationException("Invalid year format in CSV")
    }
    // Skip the second line (the header)
    val employeeLines = lines.drop(2)

    for (line in employeeLines) {
        val parts = line.split(",")
        if (parts.size != 2) {
            throw UnsupportedMediaTypeException("Malformed CSV: each employee line must have exactly 2 columns")
        }

        val email = parts[0]
        val noOfDays = parts[1].toIntOrNull()

        if (!isValidEmail(email)) {
            throw ValidationException("Invalid email format: $email")
        }

        if (noOfDays == null || noOfDays < 0) {
            throw ValidationException("Invalid number of vacation days: $noOfDays")
        }

        vacations.add(VacationRequestDTO(noOfDays = noOfDays, year = year, employeeEmail = email))
    }

    return vacations
}

fun parseUsedDays(data: ByteArray): List<UsedDaysRequestDTO> {
    val usedDays = mutableListOf<UsedDaysRequestDTO>()
    val inputStream = ByteArrayInputStream(data)
    val reader = InputStreamReader(inputStream)
    val parser = CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")

    for (record in parser) {
        val email = record.get(0)
        val beginDate = record.get(1)
        val endDate = record.get(2)

        if (!isValidEmail(email)) {
            throw ValidationException("Invalid email format: $email")
        }

        val parsedBeginDate = LocalDate.parse(beginDate, formatter)
        val parsedEndDate = LocalDate.parse(endDate, formatter)

        usedDays.add(
            UsedDaysRequestDTO(
                beginDate = parsedBeginDate,
                endDate = parsedEndDate,
                employeeEmail = email,
            ),
        )
    }
    return usedDays
}
