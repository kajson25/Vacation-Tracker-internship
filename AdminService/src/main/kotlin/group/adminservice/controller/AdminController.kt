package group.adminservice.controller

import group.adminservice.service.AdminService
import group.adminservice.service.EmployeeDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val adminService: AdminService
) {
    @GetMapping("/allEmployees")
    fun getAllEmployees(): EmployeeDetails = adminService.getEmployeeWithDetails(1)
}
