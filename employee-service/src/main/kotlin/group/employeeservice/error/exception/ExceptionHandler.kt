package group.employeeservice.error.exception

import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    private val log = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ExceptionHandler(ResourceNotFoundException::class)
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "404", description = "Resource not found"),
        ],
    )
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<String> {
        log.error("Resource not found: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(FileStorageException::class)
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "422", description = "Database error or file storage issue"),
        ],
    )
    fun handleFileStorageException(ex: FileStorageException): ResponseEntity<String> {
        log.error("DB error: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(BadRequestException::class)
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "400", description = "Bad request - invalid input data"),
        ],
    )
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<String> {
        log.error("Bad request: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ValidationException::class)
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "400", description = "Validation error - invalid data"),
        ],
    )
    fun handleValidationException(ex: ValidationException): ResponseEntity<String> {
        log.error("Invalid data: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UnauthorizedException::class)
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "401", description = "Unauthorized access"),
        ],
    )
    fun handleUnauthorizedException(ex: UnauthorizedException): ResponseEntity<String> {
        log.error("Unauthorized: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(AuthenticationException::class)
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "401", description = "Authentication failed"),
        ],
    )
    fun handleAuthenticationException(ex: AuthenticationException): ResponseEntity<String> {
        log.error("Authentication failed: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(UnsupportedMediaTypeException::class)
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "415", description = "Unsupported media type"),
        ],
    )
    fun handleUnsupportedMediaTypeException(ex: UnsupportedMediaTypeException): ResponseEntity<String> {
        log.error("Wrong data format: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    }

    @ExceptionHandler(Exception::class)
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "500", description = "Internal server error"),
        ],
    )
    fun handleGenericException(ex: Exception): ResponseEntity<String> {
        log.error("An error occurred: ${ex.message}")
        return ResponseEntity("An error occurred: ${ex.message}", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
