package group.adminservice.controller

import group.adminservice.dto.*
import group.adminservice.service.AdminService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Controller", description = "Operations related to administrative tasks")
class AdminController(
    private val adminService: AdminService,
) {
    @Operation(summary = "Import Employees", description = "Import employee data from a CSV file")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Employees imported successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = EmployeeResponseDTO::class))],
            ),
            ApiResponse(responseCode = "400", description = "Invalid CSV data provided"),
            ApiResponse(responseCode = "500", description = "Internal server error"),
        ],
    )
    @PostMapping("/importEmployees", consumes = ["text/csv"])
    fun importEmployees(
        @Parameter(description = "CSV file content", required = true)
        @RequestBody data: ByteArray,
        response: HttpServletResponse,
    ): ResponseEntity<List<EmployeeResponseDTO>> = ResponseEntity.ok(adminService.importEmployees(data))

    @Operation(summary = "Import Vacations", description = "Import vacation data from a CSV file")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Vacations imported successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = VacationResponseDTO::class))],
            ),
            ApiResponse(responseCode = "400", description = "Invalid CSV data provided"),
            ApiResponse(responseCode = "500", description = "Internal server error"),
        ],
    )
    @PostMapping("/importVacations", consumes = ["text/csv"])
    fun importVacations(
        @Parameter(description = "CSV file content", required = true)
        @RequestBody data: ByteArray,
        response: HttpServletResponse,
    ): ResponseEntity<List<VacationResponseDTO>> = ResponseEntity.ok(adminService.importVacations(data))

    @Operation(summary = "Import Used Days", description = "Import used vacation days data from a CSV file")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Used days imported successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = UsedDaysResponseDTO::class))],
            ),
            ApiResponse(responseCode = "400", description = "Invalid CSV data provided"),
            ApiResponse(responseCode = "500", description = "Internal server error"),
        ],
    )
    @PostMapping("/importUsedDays", consumes = ["text/csv"])
    fun importUsedDays(
        @Parameter(description = "CSV file content", required = true)
        @RequestBody data: ByteArray,
        response: HttpServletResponse,
    ): ResponseEntity<List<UsedDaysResponseDTO>> = ResponseEntity.ok(adminService.importUsedDays(data))

    @Operation(summary = "Get All Employees", description = "Retrieve all employees")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Employees retrieved successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = EmployeeResponseDTO::class))],
            ),
            ApiResponse(responseCode = "500", description = "Internal server error"),
        ],
    )
    @GetMapping("/allEmployees", produces = ["application/json"])
    fun allEmployees(response: HttpServletResponse): ResponseEntity<List<EmployeeResponseDTO>> =
        ResponseEntity.ok(adminService.getAllEmployees())
}

