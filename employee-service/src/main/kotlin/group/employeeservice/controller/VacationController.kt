package group.employeeservice.controller

import group.employeeservice.service.vacation.VacationService
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
@Tag(name = "Vacation Controller", description = "Operations related to employee vacation days")
class VacationController(
    private val vacationService: VacationService,
) {
    @Operation(summary = "Get Used Days", description = "Retrieve the number of used vacation days for a given year")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Used vacation days retrieved successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Int::class))],
            ),
            ApiResponse(responseCode = "401", description = "Unauthorized access"),
            ApiResponse(responseCode = "500", description = "Internal server error"),
        ],
    )
    @GetMapping("/usedDays")
    fun getUsedDays(
        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String,
        @Parameter(description = "Year for which used days are being retrieved", required = true)
        @RequestParam year: Int,
        response: HttpServletResponse,
    ): ResponseEntity<Int> = ResponseEntity.ok(vacationService.getUsedDays(token, year))

    @Operation(summary = "Get All Vacation Days", description = "Retrieve the total number of vacation days for a given year")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "All vacation days retrieved successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Int::class))],
            ),
            ApiResponse(responseCode = "401", description = "Unauthorized access"),
            ApiResponse(responseCode = "500", description = "Internal server error"),
        ],
    )
    @GetMapping("/allVacationDays")
    fun getAllVacationDays(
        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String,
        @Parameter(description = "Year for which all vacation days are being retrieved", required = true)
        @RequestParam year: Int,
        response: HttpServletResponse,
    ): ResponseEntity<Int> = ResponseEntity.ok(vacationService.getAllVacationDays(token, year))

    @Operation(summary = "Get Available Days", description = "Retrieve the number of available vacation days for a given year")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Available vacation days retrieved successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Int::class))],
            ),
            ApiResponse(responseCode = "401", description = "Unauthorized access"),
            ApiResponse(responseCode = "500", description = "Internal server error"),
        ],
    )
    @GetMapping("/availableDays")
    fun getAvailableDays(
        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String,
        @Parameter(description = "Year for which available days are being retrieved", required = true)
        @RequestParam year: Int,
        response: HttpServletResponse,
    ): ResponseEntity<Int> = ResponseEntity.ok(vacationService.getAvailableDays(token, year))

    @Operation(summary = "Get Used Days in Period", description = "Retrieve the number of used vacation days in a given period")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Used vacation days in the period retrieved successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Int::class))],
            ),
            ApiResponse(responseCode = "401", description = "Unauthorized access"),
            ApiResponse(responseCode = "500", description = "Internal server error"),
        ],
    )
    @GetMapping("/usedDaysInPeriod")
    fun getUsedDaysInPeriod(
        @Parameter(description = "Authorization token", required = true)
        @RequestHeader("Authorization") token: String,
        @Parameter(description = "Start date of the period (yyyy-MM-dd)", required = true)
        @RequestParam startDate: String,
        @Parameter(description = "End date of the period (yyyy-MM-dd)", required = true)
        @RequestParam endDate: String,
        response: HttpServletResponse,
    ): ResponseEntity<Int> = ResponseEntity.ok(vacationService.getUsedDaysInPeriod(token, startDate, endDate))
}

