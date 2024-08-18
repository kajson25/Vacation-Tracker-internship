package group.employeeservice.security

import group.employeeservice.dto.EmployeeResponseDTO
import group.employeeservice.error.logger.logger
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(
    @Value("\${oauth.jwt.secret}") private val secretKey: String,
) {
    private val log = logger<JwtUtil>()

    fun generateToken(email: String): String {
        val claims: MutableMap<String, Any> = HashMap()
        return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(email)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours validity
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun extractEmail(token: String): String {
        log.info("Extracting email from token: $token")
        return extractClaim(token) { claims: Claims -> claims.subject }
    }

    fun isTokenValid(
        token: String,
        employee: EmployeeResponseDTO,
    ): Boolean {
        val email = extractEmail(token)
        log.info("Checking the validity of the token: $token")
        return email == employee.email && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean = extractExpiration(token).before(Date())

    private fun extractExpiration(token: String): Date = extractClaim(token) { claims: Claims -> claims.expiration }

    private fun <T> extractClaim(
        token: String,
        claimsResolver: (Claims) -> T,
    ): T {
        var temp = token
        if (token.contains("Bearer")) {
            temp = token.substringAfter("Bearer ")
        }
        val claims =
            Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(temp)
                .body
        return claimsResolver(claims)
    }
}
