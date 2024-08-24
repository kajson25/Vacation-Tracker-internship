package group.employeeservice.error.exception

open class CustomException(
    message: String,
) : RuntimeException(message)

class ResourceNotFoundException(
    message: String,
) : CustomException(message)

// 400 Bad Request.
class BadRequestException(
    message: String,
) : CustomException(message)

class UnauthorizedException(
    message: String,
) : CustomException(message)

class AuthenticationException(
    message: String,
) : CustomException(message)

class UnsupportedMediaTypeException(
    message: String,
) : CustomException(message)

class ValidationException(
    message: String,
) : CustomException(message)

class FileStorageException(
    message: String,
) : CustomException(message)
