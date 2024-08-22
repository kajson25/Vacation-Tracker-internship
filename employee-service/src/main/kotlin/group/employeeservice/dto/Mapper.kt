package group.employeeservice.dto

import group.employeeservice.database.model.Admin
import group.employeeservice.database.model.Employee
import group.employeeservice.database.model.UsedDays
import group.employeeservice.database.model.Vacation
import org.springframework.stereotype.Component

@Component
class Mapper {
    fun mapEmployeeRequestToEmployee(
        employeeRequestDTO: EmployeeRequestDTO,
        admin: Admin,
    ): Employee = Employee(email = employeeRequestDTO.email, password = employeeRequestDTO.password, admin = admin)

    fun mapEmployeeToEmployeeResponse(employee: Employee) = EmployeeResponseDTO(email = employee.email, password = employee.password)

    fun mapVacationRequestToVacation(
        vacationRequestDTO: VacationRequestDTO,
        employee: Employee,
    ): Vacation = Vacation(noOfDays = vacationRequestDTO.noOfDays, year = vacationRequestDTO.year, employee = employee)

    fun mapVacationToVacationResponse(vacation: Vacation): VacationResponseDTO =
        VacationResponseDTO(noOfDays = vacation.noOfDays, year = vacation.year)

    fun mapUsedDaysRequestToUsedDays(
        usedDaysDTO: UsedDaysRequestDTO,
        employee: Employee,
    ): UsedDays = UsedDays(beginDate = usedDaysDTO.beginDate, endDate = usedDaysDTO.endDate, employee = employee)

    fun mapUsedDaysToUsedDaysResponse(usedDays: UsedDays): UsedDaysResponseDTO? =
        usedDays.endDate?.let { usedDays.beginDate?.let { it1 -> UsedDaysResponseDTO(beginDate = it1, endDate = it) } }
}
