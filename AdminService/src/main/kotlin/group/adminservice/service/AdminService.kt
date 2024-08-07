package group.adminservice.service

import group.adminservice.database.model.Admin
import group.adminservice.database.model.Employee
import group.adminservice.database.model.UsedDays
import group.adminservice.database.model.Vacation

interface AdminService {
    fun getAllEmployees(): List<Employee>

    fun importVacations(data: ByteArray): List<Vacation>

    fun importUsedDays(data: ByteArray): List<UsedDays>

    fun importEmployees(data: ByteArray): List<Employee>

    fun getAdminById(id: Long): Admin
}
