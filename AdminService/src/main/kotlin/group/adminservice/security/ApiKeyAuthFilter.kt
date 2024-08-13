package group.adminservice.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.stereotype.Component

/**
 * The ApiKeyAuthFilter intercepts incoming requests and checks for a valid API key in the X-API-KEY header.
 */
@Component
class ApiKeyAuthFilter(
    private val apiKeyService: ApiKeyService,
    authenticationManager: AuthenticationManager,
) : AbstractPreAuthenticatedProcessingFilter() {
    init {
        setAuthenticationManager(authenticationManager) // Set the AuthenticationManager in the filter
    }

    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): Any? {
        val header = request.getHeader("X-Api-Key")
        println("header: $header")
        return header
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): Any {
        return ""
    }

    override fun doFilter(
        request: ServletRequest?,
        response: ServletResponse?,
        chain: FilterChain?,
    ) {
        println("request : $request")
        if (request is HttpServletRequest && response is HttpServletResponse && chain != null) {
            val apiKey = request.getHeader("X-API-KEY")
            if (apiKey != null && apiKeyService.isValidApiKey(apiKey)) {
                val auth = ApiKeyAuthenticationToken(apiKey, null, emptyList())
                auth.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = auth
            }
            chain.doFilter(request, response)
        }
    }
}
