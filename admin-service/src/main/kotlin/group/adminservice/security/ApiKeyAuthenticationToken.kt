package group.adminservice.security

import org.springframework.security.authentication.AbstractAuthenticationToken

class ApiKeyAuthenticationToken(
    private val apiKey: String,
) : AbstractAuthenticationToken(null) {
    init {
        isAuthenticated = true
    }

    override fun getCredentials(): Any = apiKey

    override fun getPrincipal(): Any = apiKey

    override fun setAuthenticated(isAuthenticated: Boolean) {
        super.setAuthenticated(isAuthenticated)
    }
}
