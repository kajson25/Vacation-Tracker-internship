package group.employeeservice.security

import group.employeeservice.error.exception.AuthenticationException
import group.employeeservice.service.employee.EmployeeService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtUtil: JwtUtil,
    private val employeeService: EmployeeService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
    ) {
        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val jwt = authorizationHeader.substring(7).trim()
            val email = jwtUtil.extractEmail(jwt)

            if (email.isNotEmpty() && SecurityContextHolder.getContext().authentication == null) {
                val employee = employeeService.findByEmail(email)
                if (jwtUtil.isTokenValid(jwt, employee)) {
                    val authToken =
                        UsernamePasswordAuthenticationToken(
                            employee,
                            null,
                            emptyList(), // Passing an empty list for authorities
                        )

                    SecurityContextHolder.getContext().authentication = authToken
                } else {
                    throw AuthenticationException("Invalid token")
                }
            }
        }
        chain.doFilter(request, response)
    }
}
