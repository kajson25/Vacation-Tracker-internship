package group.employeeservice.service.usedDays

import group.employeeservice.dto.Mapper
import group.employeeservice.dto.UsedDaysDTO
import group.employeeservice.error.exception.BadRequestException
import group.employeeservice.error.exception.ResourceNotFoundException
import group.employeeservice.error.logger.logger
import group.employeeservice.helper.Parser
import group.employeeservice.repository.EmployeeRepository
import group.employeeservice.repository.UsedDaysRepository
import group.employeeservice.security.JwtUtil
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.io.IOException

@Service
@Transactional
class UsedDaysServiceImplementation(
    private val usedDaysRepository: UsedDaysRepository,
    private val employeeRepository: EmployeeRepository,
    private val mapper: Mapper,
    private val jwtUtil: JwtUtil,
) : UsedDaysService {
    val parser: Parser = Parser()
    private val log = logger<UsedDaysService>()

    @Throws(IOException::class, ResourceNotFoundException::class, BadRequestException::class)
    override fun addUsedDay(
        data: ByteArray,
        token: String,
    ): UsedDaysDTO? {
        if (data.isEmpty()) {
            throw BadRequestException("CSV data cannot be empty")
        }
        val email = jwtUtil.extractEmail(token)
        val usedDay =
            parser.parseUsedDays(
                data,
                employeeRepository.findByEmail(email).orElseThrow { ResourceNotFoundException("Employee not found: $email") },
            )
        log.info("Parsed used days")

        return usedDay?.let { mapper.mapUsedDays(usedDaysRepository.save(it)) }
    }
}
