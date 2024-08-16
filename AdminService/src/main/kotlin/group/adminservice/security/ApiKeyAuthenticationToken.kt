package group.adminservice.security
import org.springframework.security.authentication.AbstractAuthenticationToken

class ApiKeyAuthenticationToken(
    private val apiKey: String,
    // private val apiSecret: String,
    // authorities: Collection<GrantedAuthority>? = null,
) : AbstractAuthenticationToken(null) {
    init {
        // isAuthenticated = authorities != null
        isAuthenticated = true
    }

    override fun getCredentials(): Any = apiKey

    override fun getPrincipal(): Any = apiKey

    override fun setAuthenticated(isAuthenticated: Boolean) {
        if (isAuthenticated) {
            super.setAuthenticated(true)
        } else {
            super.setAuthenticated(false)
        }
    }
}
