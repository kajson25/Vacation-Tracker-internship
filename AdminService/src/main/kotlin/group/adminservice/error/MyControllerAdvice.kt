package group.adminservice.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class MyControllerAdvice {
    // No entities like that in db
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.NOT_FOUND)

    // DB errors
    @ExceptionHandler(FileStorageException::class)
    fun handleFileStorageException(ex: FileStorageException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.UNPROCESSABLE_ENTITY)

    // Got wrong data from the outside
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<String> = ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)

    // Model @Valid errors todo?
    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException): ResponseEntity<String> = ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)

    // Security
    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(ex: UnauthorizedException): ResponseEntity<String> = ResponseEntity(ex.message, HttpStatus.UNAUTHORIZED)

    // Security
    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(ex: AuthenticationException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.UNAUTHORIZED)

    // Not csv
    @ExceptionHandler(UnsupportedMediaTypeException::class)
    fun handleUnsupportedMediaTypeException(ex: UnsupportedMediaTypeException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.UNSUPPORTED_MEDIA_TYPE)

    // Retry pattern
    @ExceptionHandler(ServiceUnavailableException::class)
    fun handleServiceUnavailableException(ex: ServiceUnavailableException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.SERVICE_UNAVAILABLE)

    // Retry pattern
    @ExceptionHandler(TimeoutException::class)
    fun handleTimeoutException(ex: TimeoutException): ResponseEntity<String> = ResponseEntity(ex.message, HttpStatus.REQUEST_TIMEOUT)

    // Other
    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<String> =
        ResponseEntity("An error occurred: ${ex.message}", HttpStatus.INTERNAL_SERVER_ERROR)
}
