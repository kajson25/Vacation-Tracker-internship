package group.adminservice.controller

import group.adminservice.dto.EmployeeDTO
import group.adminservice.dto.UsedDaysDTO
import group.adminservice.dto.VacationDTO
import group.adminservice.service.AdminService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val adminService: AdminService,
) {
    @PostMapping("/importEmployees", consumes = ["text/csv"])
    fun importEmployees(
        @RequestBody data: ByteArray,
        response: HttpServletResponse,
    ): ResponseEntity<List<EmployeeDTO>> {
        val contentType = "text/csv"
        response.contentType = contentType
        return ResponseEntity.ok(adminService.importEmployees(data))
    }

    @PostMapping("/importVacations", consumes = ["text/csv"])
    fun importVacations(
        @RequestBody data: ByteArray,
        response: HttpServletResponse,
    ): ResponseEntity<List<VacationDTO>> {
        val contentType = "text/csv"
        response.contentType = contentType
        return ResponseEntity.ok(adminService.importVacations(data))
    }

    @PostMapping("/importUsedDays", consumes = ["text/csv"])
    fun importUsedDays(
        @RequestBody data: ByteArray,
        response: HttpServletResponse,
    ): ResponseEntity<List<UsedDaysDTO>> {
        val contentType = "text/csv"
        response.contentType = contentType
        return ResponseEntity.ok(adminService.importUsedDays(data))
    }

    @GetMapping("/allEmployees", produces = ["application/json"])
    fun allEmployees(response: HttpServletResponse): ResponseEntity<List<EmployeeDTO>> = ResponseEntity.ok(adminService.getAllEmployees())
}
