package group.adminservice.dto

import java.sql.Date

data class EmployeeDTO(
    val employeeId: Long,
    val email: String,
)

data class VacationDTO(
    var noOfDays: Int,
    var year: Int,
    var employeeId: Long,
)

data class UsedDaysDTO(
    var beginDate: Date,
    var endDate: Date,
    var employeeId: Long,
)
