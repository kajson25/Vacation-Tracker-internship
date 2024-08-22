package group.employeeservice.service.usedDays

import group.employeeservice.dto.Mapper
import group.employeeservice.dto.UsedDaysResponseDTO
import group.employeeservice.error.exception.BadRequestException
import group.employeeservice.error.exception.ResourceNotFoundException
import group.employeeservice.error.logger.logger
import group.employeeservice.helper.parseUsedDays
import group.employeeservice.repository.EmployeeRepository
import group.employeeservice.repository.UsedDaysRepository
import group.employeeservice.security.JwtUtil
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class UsedDaysServiceImplementation(
    private val usedDaysRepository: UsedDaysRepository,
    private val employeeRepository: EmployeeRepository,
    private val mapper: Mapper,
    private val jwtUtil: JwtUtil,
) : UsedDaysService {
    private val log = logger<UsedDaysService>()

    @Throws(ResourceNotFoundException::class, BadRequestException::class)
    override fun addUsedDay(
        data: ByteArray,
        token: String,
    ): UsedDaysResponseDTO {
        if (data.isEmpty()) {
            throw BadRequestException("CSV data cannot be empty")
        }
        val email = jwtUtil.extractEmail(token)
        val usedDay =
            parseUsedDays(
                data,
                email,
            )
        log.info("Parsed used days")
        val emp = employeeRepository.findByEmail(email).get()
        val usedDayModel = mapper.mapUsedDaysRequestToUsedDays(usedDay, emp)
        emp.usedDays = emp.usedDays.plus(usedDayModel)
        val res = usedDaysRepository.save(usedDayModel)
        log.info("Saved used day")

        return mapper.mapUsedDaysToUsedDaysResponse(res)!!
    }
}
