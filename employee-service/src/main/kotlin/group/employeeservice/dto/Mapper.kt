package group.employeeservice.dto

import group.employeeservice.database.model.Employee
import group.employeeservice.database.model.UsedDays
import group.employeeservice.database.model.Vacation
import org.springframework.stereotype.Component

@Component
class Mapper {
    fun mapEmployeeToResponse(employee: Employee): EmployeeResponseDTO =
        EmployeeResponseDTO(employeeId = employee.employee_id, email = employee.email, password = employee.password)

    fun mapRequestToEmployee(employeeRequestDTO: EmployeeRequestDTO): Employee = Employee(email = employeeRequestDTO.email)

    fun mapVacation(vacation: Vacation): VacationDTO? =
        vacation.employee?.let { VacationDTO(noOfDays = vacation.noOfDays, year = vacation.year, employeeId = it.employee_id) }

    fun mapUsedDays(usedDays: UsedDays): UsedDaysDTO? =
        usedDays.employee?.let {
            usedDays.beginDate?.let { it1 ->
                usedDays.endDate?.let { it2 ->
                    UsedDaysDTO(
                        beginDate = it1.toLocalDate(),
                        endDate = it2.toLocalDate(),
                        employeeId = it.employee_id,
                    )
                }
            }
        }
}
