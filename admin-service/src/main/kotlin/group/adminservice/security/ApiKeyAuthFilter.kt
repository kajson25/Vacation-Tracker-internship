package group.adminservice.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.ServletException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import java.io.IOException
import group.adminservice.error.exceptions.AuthenticationException
import group.adminservice.error.exceptions.UnauthorizedException
import group.adminservice.error.logger.logger

/**
 * The ApiKeyAuthFilter intercepts incoming requests and checks for a valid API key in the X-API-KEY header.
 */
class ApiKeyAuthFilter(
    requiresAuth: RequestMatcher,
) : AbstractAuthenticationProcessingFilter(requiresAuth) {
    companion object {
        private const val API_KEY_HEADER = "API-Key"
        // private const val API_SECRET_HEADER = "API-Secret"
    }

    private val log = logger<ApiKeyAuthFilter>()

    @Throws(AuthenticationException::class, IOException::class, UnauthorizedException::class)
    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): Authentication {
        val apiKey =
            request.getHeader(API_KEY_HEADER) ?: // || apiSecret == null) {
                throw AuthenticationException("Missing API Key or Secret")
        // val apiSecret = request.getHeader(API_SECRET_HEADER)

        val auth = ApiKeyAuthenticationToken(apiKey)
        log.info("Attempting authentication")
        return authenticationManager.authenticate(auth)
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication,
    ) {
        SecurityContextHolder.getContext().authentication = authResult
        chain.doFilter(request, response)
        log.info("Successfully authenticated")
    }
}
