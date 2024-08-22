package group.employeeservice.helper

import group.employeeservice.dto.UsedDaysRequestDTO
import group.employeeservice.error.exception.ValidationException
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

fun parseUsedDays(
    data: ByteArray,
    email: String,
): UsedDaysRequestDTO {
    val inputStream = ByteArrayInputStream(data)
    val reader = InputStreamReader(inputStream)
    val parser = CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")
    val record = parser.records[0]

    // val email = record.get(0)
    val beginDate = record.get(0)
    val endDate = record.get(1)

    if (!isValidEmail(email)) {
        throw ValidationException("Invalid email format: $email")
    }

    val parsedBeginDate = LocalDate.parse(beginDate, formatter)
    val parsedEndDate = LocalDate.parse(endDate, formatter)

    val usedDays =
        UsedDaysRequestDTO(
            beginDate = parsedBeginDate,
            endDate = parsedEndDate,
            employeeEmail = email,
        )

    return usedDays
}
