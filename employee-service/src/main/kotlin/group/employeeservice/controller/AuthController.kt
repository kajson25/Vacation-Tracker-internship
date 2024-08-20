package group.employeeservice.controller

import group.employeeservice.dto.AuthResponse
import group.employeeservice.dto.EmployeeRequestDTO
import group.employeeservice.security.JwtUtil
import group.employeeservice.service.employee.EmployeeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Tag(name = "Authentication Controller", description = "Operations related to user authentication")
class AuthController(
    private val employeeService: EmployeeService,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: BCryptPasswordEncoder,
) {
    @Operation(summary = "Authenticate User", description = "Authenticate the user and generate a JWT token")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Authentication successful, JWT token generated",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = AuthResponse::class))],
            ),
            ApiResponse(responseCode = "400", description = "Invalid credentials provided"),
            ApiResponse(responseCode = "500", description = "Internal server error"),
        ],
    )
    @PostMapping("/authenticate")
    fun authorize(
        @Parameter(description = "Authentication request containing email and password", required = true)
        @RequestBody employeeRequest: EmployeeRequestDTO,
    ): ResponseEntity<Any> {
        val employeeResponse =
            employeeService
                .findByEmail(employeeRequest.email)

        return if (passwordEncoder.matches(employeeRequest.password, employeeResponse.password)) {
            val token = jwtUtil.generateToken(employeeResponse.email)
            return ResponseEntity.ok(AuthResponse(token))
        } else {
            ResponseEntity.badRequest().body("Invalid credentials")
        }
    }
}
