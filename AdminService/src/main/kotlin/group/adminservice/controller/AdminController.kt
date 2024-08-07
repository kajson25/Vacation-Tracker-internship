package group.adminservice.controller

import group.adminservice.database.model.Employee
import group.adminservice.database.model.UsedDays
import group.adminservice.database.model.Vacation
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
    ): ResponseEntity<List<Employee>> {
        val contentLength = data.size
        val contentType = "text/csv"
        response.setContentLength(contentLength)
        response.contentType = contentType
        return ResponseEntity.ok(adminService.importEmployees(data))
    }

    @PostMapping("/importVacations", consumes = ["text/csv"])
    fun importVacations(
        @RequestBody data: ByteArray,
        response: HttpServletResponse,
    ): ResponseEntity<List<Vacation>> {
        val contentLength = data.size
        val contentType = "text/csv"
        response.setContentLength(contentLength)
        response.contentType = contentType
        return ResponseEntity.ok(adminService.importVacations(data))
    }

    @PostMapping("/importUsedDays", consumes = ["text/csv"])
    fun importUsedDays(
        @RequestBody data: ByteArray,
        response: HttpServletResponse,
    ): ResponseEntity<List<UsedDays>> {
        val contentLength = data.size
        val contentType = "text/csv"
        response.setContentLength(contentLength)
        response.contentType = contentType
        return ResponseEntity.ok(adminService.importUsedDays(data))
    }
}
