package group.employeeservice.error.exception

open class EmployeeException(
    message: String,
) : RuntimeException(message)

class ResourceNotFoundException(
    message: String,
) : EmployeeException(message)

// 400 Bad Request.
class BadRequestException(
    message: String,
) : EmployeeException(message)

class AuthenticationException(
    message: String,
) : EmployeeException(message)

class UnsupportedMediaTypeException(
    message: String,
) : EmployeeException(message)

class ValidationException(
    message: String,
) : EmployeeException(message)

class FileStorageException(
    message: String,
) : EmployeeException(message)
