package group.adminservice.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class ApiKeyAuthenticationToken(
    private val principal: String?,
    private val credentials: String?,
    authorities: Collection<GrantedAuthority>?,
) : AbstractAuthenticationToken(authorities) {
    override fun getCredentials(): Any? = credentials

    override fun getPrincipal(): Any? = principal
}
