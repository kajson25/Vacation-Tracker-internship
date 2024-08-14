package group.adminservice.security.api

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

/**
 * The SecurityConfig class configures how requests to your application are secured.
 * The authorizeHttpRequests configuration defines that any request to /admin/\** must be authenticated (i.e., must pass the API key check).
 * All other requests are permitted.
 */
@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Value("\${milan.gospodeboze.pomiluj}") private val myApiKey: String
) {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val filter = ApiKeyAuthFilter(AntPathRequestMatcher("/api/admin/**"))
        filter.setAuthenticationManager { authentication ->
            val apiKey = authentication.principal as String
            // val apiSecret = authentication.credentials as String

            if (myApiKey == apiKey) { // && "valid-api-secret" == apiSecret) {
                ApiKeyAuthenticationToken(
                    apiKey,
                    // apiSecret,
                    listOf(SimpleGrantedAuthority("ROLE_ADMIN")),
                )
            } else {
                authentication.isAuthenticated = false
                throw AuthenticationServiceException("Invalid API key")
                // authentication
            }
        }

        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/api/admin/**")
                    .hasRole("ADMIN")
                    // .authenticated()
                    .anyRequest()
                    .permitAll()
            }.addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
    /*
    .csrf { csrf -> csrf.disable() }
        .sessionManagement { session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .addFilterBefore(apiKeyAuthFilter(authenticationManager(authenticationConfiguration())), UsernamePasswordAuthenticationFilter::class.java)
        .authorizeHttpRequests { authorize ->
            authorize
                .requestMatchers("/admin/\\**")
                .authenticated()
                .anyRequest()
                .permitAll()
        }
     */
