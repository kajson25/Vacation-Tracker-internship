package group.adminservice.dto

import group.adminservice.database.model.Employee
import group.adminservice.database.model.UsedDays
import group.adminservice.database.model.Vacation
import org.springframework.stereotype.Component

@Component
class Mapper {
    fun mapEmployee(employee: Employee): EmployeeDTO = EmployeeDTO(employeeId = employee.employee_id, email = employee.email)

    fun mapVacation(vacation: Vacation): VacationDTO? =
        vacation.employee?.let { VacationDTO(noOfDays = vacation.noOfDays, year = vacation.year, employeeId = it.employee_id) }

    fun mapUsedDays(usedDays: UsedDays): UsedDaysDTO? =
        usedDays.employee?.let { usedDays.beginDate?.let { it1 -> usedDays.endDate?.let { it2 -> UsedDaysDTO(beginDate = it1, endDate = it2, employeeId = it.employee_id) } } }

}
