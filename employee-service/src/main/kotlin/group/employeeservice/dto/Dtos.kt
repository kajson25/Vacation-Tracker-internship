package group.employeeservice.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

@Schema(description = "Data Transfer Object representing an employee")
data class EmployeeRequestDTO(
    @Schema(description = "The email address of the employee", example = "john.doe@example.com", required = true)
    val email: String,
    @Schema(description = "Non-ecrypted raw employee password", example = "password123", required = true)
    var password: String,
)

@Schema(description = "Data Transfer Object representing an employee")
data class EmployeeResponseDTO(
    @Schema(description = "The email address of the employee", example = "john.doe@example.com", required = true)
    val email: String,
    @Schema(description = "Non-ecrypted raw employee password", example = "password123", required = true)
    var password: String,
)

@Schema(description = "Data Transfer Object representing vacation details")
data class VacationRequestDTO(
    @Schema(description = "The number of vacation days", example = "10", required = true)
    var noOfDays: Int,
    @Schema(description = "The year for which the vacation days are allocated", example = "2023", required = true)
    var year: Int,
    @Schema(description = "The email address of the employee", example = "john.doe@example.com", required = true)
    var employeeEmail: String,
)

@Schema(description = "Data Transfer Object representing vacation details")
data class VacationResponseDTO(
    @Schema(description = "The number of vacation days", example = "10", required = true)
    var noOfDays: Int,
    @Schema(description = "The year for which the vacation days are allocated", example = "2023", required = true)
    var year: Int,
)

@Schema(description = "Data Transfer Object representing used vacation days")
data class UsedDaysRequestDTO(
    @Schema(description = "The start date of the vacation period", example = "2023-08-01", required = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "EEEE, MMMM d, yyyy")
    var beginDate: LocalDate,
    @Schema(description = "The end date of the vacation period", example = "2023-08-10", required = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "EEEE, MMMM d, yyyy")
    var endDate: LocalDate,
    @Schema(description = "The email address of the employee", example = "john.doe@example.com", required = true)
    var employeeEmail: String,
)

@Schema(description = "Data Transfer Object representing used vacation days")
data class UsedDaysResponseDTO(
    @Schema(description = "The start date of the vacation period", example = "2023-08-01", required = true)
    var beginDate: LocalDate,
    @Schema(description = "The end date of the vacation period", example = "2023-08-10", required = true)
    var endDate: LocalDate,
)

@Schema(description = "Response for authenticating an employee")
data class AuthResponse(
    @Schema(description = "JWT token based on credentials", example = "Bearer eyahifnjfniuq...", required = true)
    val token: String,
)
