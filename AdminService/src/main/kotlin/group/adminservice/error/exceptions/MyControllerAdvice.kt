package group.adminservice.error.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class MyControllerAdvice {
    private val log = LoggerFactory.getLogger(MyControllerAdvice::class.java)

    // No entities like that in db
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<String> {
        log.error("Resource not found: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }

    // DB errors
    @ExceptionHandler(FileStorageException::class)
    fun handleFileStorageException(ex: FileStorageException): ResponseEntity<String> {
        log.error("DB error: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    // Got wrong data from the outside
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<String> {
        log.error("Bad request: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    // Model @Valid errors todo?
    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException): ResponseEntity<String> {
        log.error("Invalid data: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    // Security
    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(ex: UnauthorizedException): ResponseEntity<String> {
        log.error("Unauthorized: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.UNAUTHORIZED)
    }

    // Security
    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(ex: AuthenticationException): ResponseEntity<String> {
        log.error("Authentication failed: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.UNAUTHORIZED)
    }

    // Not csv
    @ExceptionHandler(UnsupportedMediaTypeException::class)
    fun handleUnsupportedMediaTypeException(ex: UnsupportedMediaTypeException): ResponseEntity<String> {
        log.error("Wrong data format: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    }

    // Retry pattern
    @ExceptionHandler(ServiceUnavailableException::class)
    fun handleServiceUnavailableException(ex: ServiceUnavailableException): ResponseEntity<String> {
        log.error("Service is currently unavailable: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.SERVICE_UNAVAILABLE)
    }

    // Retry pattern
    @ExceptionHandler(TimeoutException::class)
    fun handleTimeoutException(ex: TimeoutException): ResponseEntity<String> {
        log.error("Request timeout: ${ex.message}")
        return ResponseEntity(ex.message, HttpStatus.REQUEST_TIMEOUT)
    }

    // Other
    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<String> {
        log.error("An error occurred: ${ex.message}")
        return ResponseEntity("An error occurred: ${ex.message}", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
