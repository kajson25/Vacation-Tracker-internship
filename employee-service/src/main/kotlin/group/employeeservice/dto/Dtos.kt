package group.employeeservice.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

@Schema(description = "Request object for employee authentication")
data class EmployeeRequestDTO(
    @Schema(description = "The email of the employee", example = "john.doe@example.com", required = true)
    @Email
    val email: String,
    @Schema(description = "The password of the employee", example = "password123", required = true)
    val password: String,
)

@Schema(description = "Response object containing employee information")
data class EmployeeResponseDTO(
    @Schema(description = "The unique ID of the employee", example = "1", required = true)
    val employeeId: Long,
    @Schema(description = "The email of the employee", example = "john.doe@example.com", required = true)
    @Email
    val email: String,
)

@Schema(description = "Response object containing the JWT token after successful authentication")
data class AuthResponse(
    @Schema(description = "JWT token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    val token: String,
)

@Schema(description = "Object representing vacation details for an employee")
data class VacationDTO(
    @Schema(description = "The number of vacation days", example = "10", required = true)
    var noOfDays: Int,
    @Schema(description = "The year for which the vacation days apply", example = "2023", required = true)
    @Min(1970)
    @Max(2030)
    var year: Int,
    @Schema(description = "The unique ID of the employee", example = "1", required = true)
    var employeeId: Long,
)

@Schema(description = "Object representing the used vacation days for an employee")
data class UsedDaysDTO(
    @Schema(description = "The start date of the vacation period", example = "2023-08-01", required = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "EEEE, MMMM d, yyyy")
    var beginDate: LocalDate,
    @Schema(description = "The end date of the vacation period", example = "2023-08-10", required = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "EEEE, MMMM d, yyyy")
    var endDate: LocalDate,
    @Schema(description = "The unique ID of the employee", example = "1", required = true)
    var employeeId: Long,
)

