package group.adminservice.controller

import group.adminservice.database.model.Employee
import group.adminservice.service.AdminService
import group.adminservice.service.AdminServiceImpl
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val adminService: AdminService,
    private val adminServiceImpl: AdminServiceImpl,
) {
    @PostMapping("/importEmployees", consumes = ["text/csv"])
    fun handleCsvUpload(
        @RequestBody data: ByteArray,
        response: HttpServletResponse,
    ): ResponseEntity<List<Employee>> {
        val contentLength = data.size
        val contentType = "text/csv"
        response.setContentLength(contentLength)
        response.contentType = contentType
        return ResponseEntity.ok(adminService.importEmployees(data))
    }
}
