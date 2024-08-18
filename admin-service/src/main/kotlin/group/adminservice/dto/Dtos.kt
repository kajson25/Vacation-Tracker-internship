package group.adminservice.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Email
import org.springframework.format.annotation.DateTimeFormat
import java.sql.Date

@Schema(description = "Data Transfer Object representing an employee")
data class EmployeeDTO(
    @Schema(description = "The unique ID of the employee", example = "1", required = true)
    val employeeId: Long,
    @Schema(description = "The email address of the employee", example = "john.doe@example.com", required = true)
    @Email
    val email: String,
)

@Schema(description = "Data Transfer Object representing vacation details")
data class VacationDTO(
    @Schema(description = "The number of vacation days", example = "10", required = true)
    @PositiveOrZero
    var noOfDays: Int,
    @Schema(description = "The year for which the vacation days are allocated", example = "2023", required = true)
    @Min(1970)
    @Max(2030)
    var year: Int,
    @Schema(description = "The unique ID of the employee", example = "1", required = true)
    var employeeId: Long,
)

@Schema(description = "Data Transfer Object representing used vacation days")
data class UsedDaysDTO(
    @Schema(description = "The start date of the vacation period", example = "2023-08-01", required = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "EEEE, MMMM d, yyyy")
    var beginDate: Date,
    @Schema(description = "The end date of the vacation period", example = "2023-08-10", required = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "EEEE, MMMM d, yyyy")
    var endDate: Date,
    @Schema(description = "The unique ID of the employee", example = "1", required = true)
    var employeeId: Long,
)

