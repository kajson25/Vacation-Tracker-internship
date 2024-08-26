package group.adminservice.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import group.adminservice.error.logger.logger

/**
 * The ApiKeyAuthFilter intercepts incoming requests and checks for a valid API key in the X-API-KEY header.
 */
class ApiKeyAuthFilter(
    requiresAuth: RequestMatcher,
) : AbstractAuthenticationProcessingFilter(requiresAuth) {
    companion object {
        private const val API_KEY_HEADER = "API-Key"
    }

    private val log = logger<ApiKeyAuthFilter>()

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): Authentication {
        val apiKey =
            request.getHeader(API_KEY_HEADER)

        val auth = ApiKeyAuthenticationToken(apiKey)
        log.info("Attempting authentication")
        return authenticationManager.authenticate(auth)
    }

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
