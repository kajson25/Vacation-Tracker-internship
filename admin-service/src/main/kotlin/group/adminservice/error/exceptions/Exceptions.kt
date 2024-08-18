package group.adminservice.error.exceptions

class ResourceNotFoundException(
    message: String,
) : RuntimeException(message)

// 400 Bad Request.
class BadRequestException(
    message: String,
) : RuntimeException(message)

class UnauthorizedException(
    message: String,
) : RuntimeException(message)

class AuthenticationException(
    message: String,
) : RuntimeException(message)

class ServiceUnavailableException(
    message: String,
) : RuntimeException(message)

class UnsupportedMediaTypeException(
    message: String,
) : RuntimeException(message)

class ValidationException(
    message: String,
) : RuntimeException(message)

class TimeoutException(
    message: String,
) : RuntimeException(message)

class FileStorageException(
    message: String,
) : RuntimeException(message)
