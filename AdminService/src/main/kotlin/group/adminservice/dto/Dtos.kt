package group.adminservice.dto

import org.springframework.format.annotation.DateTimeFormat
import java.sql.Date
import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.PositiveOrZero

data class EmployeeDTO(
    val employeeId: Long,
    @Email
    val email: String,
)

data class VacationDTO(
    @PositiveOrZero
    var noOfDays: Int,
    @Min(1970)
    @Max(2030)
    var year: Int,
    var employeeId: Long,
)

data class UsedDaysDTO(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var beginDate: Date,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var endDate: Date,
    var employeeId: Long,
)
