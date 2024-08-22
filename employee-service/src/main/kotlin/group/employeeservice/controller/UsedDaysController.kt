package group.employeeservice.controller

import group.employeeservice.dto.UsedDaysResponseDTO
import group.employeeservice.service.usedDays.UsedDaysService
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
@RequestMapping("/api/employee")
@Tag(name = "Used Days Controller", description = "Operations related to managing used vacation days")
class UsedDaysController(
    private val usedDaysService: UsedDaysService,
) {
    @Operation(summary = "Add Used Day", description = "Add a record of used vacation days by importing data from a CSV file")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Used days added successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = UsedDaysResponseDTO::class))],
            ),
            ApiResponse(responseCode = "400", description = "Invalid CSV data provided"),
            ApiResponse(responseCode = "401", description = "Unauthorized access"),
            ApiResponse(responseCode = "500", description = "Internal server error"),
        ],
    )
    @PostMapping("/addUsedDays", consumes = ["text/csv"])
    fun addUsedDay(
        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String,
        @Parameter(description = "CSV file content", required = true)
        @RequestBody data: ByteArray,
        response: HttpServletResponse,
    ): ResponseEntity<UsedDaysResponseDTO> {
        return ResponseEntity.ok(usedDaysService.addUsedDay(data, token))
    }
}

