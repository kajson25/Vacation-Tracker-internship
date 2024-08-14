package group.adminservice.security.api
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class ApiKeyAuthenticationToken(
    private val apiKey: String,
    // private val apiSecret: String,
    authorities: Collection<GrantedAuthority>? = null,
) : AbstractAuthenticationToken(authorities) {
    init {
        isAuthenticated = authorities != null
    }

    override fun getCredentials(): Any = apiKey

    override fun getPrincipal(): Any = apiKey

    override fun setAuthenticated(isAuthenticated: Boolean) {
        if (isAuthenticated) {
            throw IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead")
        }
        super.setAuthenticated(false)
    }

    override fun eraseCredentials() {
        super.eraseCredentials()
    }
}
