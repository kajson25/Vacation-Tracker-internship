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
import group.adminservice.error.AuthenticationException
import group.adminservice.error.UnauthorizedException

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
        return authenticationManager.authenticate(auth)
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication,
    ) {
        // super.successfulAuthentication(request, response, chain, authResult)
        SecurityContextHolder.getContext().authentication = authResult

        // println("Request is: ${request.method}")
        chain.doFilter(request, response)
        // println("Response is: $response")
    }
}
