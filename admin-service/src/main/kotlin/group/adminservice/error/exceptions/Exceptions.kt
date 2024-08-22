package group.adminservice.error.exceptions

open class MyException(
    message: String,
) : RuntimeException(message)

class ResourceNotFoundException(
    message: String,
) : MyException(message)

// 400 Bad Request.
class BadRequestException(
    message: String,
) : MyException(message)

class UnauthorizedException(
    message: String,
) : MyException(message)

class AuthenticationException(
    message: String,
) : MyException(message)

class UnsupportedMediaTypeException(
    message: String,
) : MyException(message)

class ValidationException(
    message: String,
) : MyException(message)

class FileStorageException(
    message: String,
) : MyException(message)
