package group.employeeservice.dto

import group.employeeservice.database.model.Employee
import group.employeeservice.database.model.UsedDays

class Mapper {
    fun mapEmployeeToEmployeeResponse(employee: Employee) = EmployeeResponseDTO(email = employee.email, password = employee.password)

    fun mapUsedDaysRequestToUsedDays(
        usedDaysDTO: UsedDaysRequestDTO,
        employee: Employee,
    ): UsedDays = UsedDays(beginDate = usedDaysDTO.beginDate, endDate = usedDaysDTO.endDate, employee = employee)

    fun mapUsedDaysToUsedDaysResponse(usedDays: UsedDays): UsedDaysResponseDTO? =
        usedDays.endDate?.let { usedDays.beginDate?.let { it1 -> UsedDaysResponseDTO(beginDate = it1, endDate = it) } }
}
