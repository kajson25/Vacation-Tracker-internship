package group.adminservice.security

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import group.adminservice.error.exceptions.AuthenticationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

/**
 * The SecurityConfig class configures how requests to your application are secured.
 * The authorizeHttpRequests configuration defines that any request to /api/admin/\\** must be authenticated (i.e., must pass the API key check).
 * All other requests are permitted.
*/
@Configuration
@EnableWebSecurity
@SecurityScheme(
    name = "apiKeyAuth",
    type = SecuritySchemeType.APIKEY,
    `in` = SecuritySchemeIn.HEADER,
    paramName = "Authorization",
)
class SecurityConfig(
    @Value("\${milan.gospodeboze.pomiluj}") private val myApiKey: String,
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val filter = ApiKeyAuthFilter(AntPathRequestMatcher("/api/admin/**"))
        filter.setAuthenticationManager { authentication ->
            val apiKey = authentication.principal as String

            if (myApiKey == apiKey) {
                ApiKeyAuthenticationToken(
                    apiKey,
                )
            } else {
                // todo - ovde excepetion sljaka savrseno
                authentication.isAuthenticated = false
                throw AuthenticationException("Invalid API key")
            }
        }

        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/api/admin/**")
                    .authenticated()
                    .anyRequest()
                    .permitAll()
            }.addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
