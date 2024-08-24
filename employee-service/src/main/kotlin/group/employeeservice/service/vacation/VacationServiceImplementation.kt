package group.employeeservice.service.vacation

import group.employeeservice.database.model.Employee
import group.employeeservice.error.exception.BadRequestException
import group.employeeservice.error.exception.ResourceNotFoundException
import group.employeeservice.error.exception.UnsupportedMediaTypeException
import group.employeeservice.error.logger.logger
import group.employeeservice.helper.Calculator
import group.employeeservice.repository.EmployeeRepository
import group.employeeservice.security.JwtUtil
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Service
@Transactional
class VacationServiceImplementation(
    private val employeeRepository: EmployeeRepository,
    private val jwtUtil: JwtUtil,
) : VacationService {
    val calculator: Calculator = Calculator()
    private val log = logger<VacationService>()

    private fun extractEmployee(token: String): Employee {
        val email = jwtUtil.extractEmail(token)
        log.info("Extracted employee: $email")
        val emp =
            employeeRepository.findByEmail(email)
                ?: throw ResourceNotFoundException("No employee with the email: $email")
        return emp
    }

    // koliko je dana iskorisceno
    @Throws(UnsupportedMediaTypeException::class, DateTimeParseException::class)
    override fun getUsedDays(
        token: String,
        year: Int,
    ): Int {
        if (year < 1950) throw BadRequestException("Invalid year")
        val emp: Employee = extractEmployee(token)
        return calculator.calculateAllUsedDays(
            emp,
            LocalDate.parse("01-01-$year", DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            LocalDate.parse("31-12-$year", DateTimeFormatter.ofPattern("dd-MM-yyyy")),
        )
    }

    // ukupno slobodnih dana u godini
    @Throws(UnsupportedMediaTypeException::class, DateTimeParseException::class)
    override fun getAllVacationDays(
        token: String,
        year: Int,
    ): Int {
        if (year < 1950) throw BadRequestException("Invalid year")
        val emp: Employee = extractEmployee(token)
        return calculator.calculateAllDays(
            emp,
            LocalDate.parse("01-01-$year", DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            LocalDate.parse("31-12-$year", DateTimeFormatter.ofPattern("dd-MM-yyyy")),
        )
    }

    // koliko je dana jos ostalo
    @Throws(UnsupportedMediaTypeException::class)
    override fun getAvailableDays(
        token: String,
        year: Int,
    ): Int {
        if (year < 1950) throw BadRequestException("Invalid year")
        val emp: Employee = extractEmployee(token)
        return calculator.calculateAvailableDays(emp, year)
    }

    @Throws(UnsupportedMediaTypeException::class, DateTimeParseException::class)
    override fun getUsedDaysInPeriod(
        token: String,
        startDate: String,
        endDate: String,
    ): Int {
        val emp: Employee = extractEmployee(token)
        val formatStart = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        val formatEnd = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        log.info("Formatted dates")

        return calculator.calculateAllDays(emp, formatStart, formatEnd)
    }
}
