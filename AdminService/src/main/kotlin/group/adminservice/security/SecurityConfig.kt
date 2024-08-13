package group.adminservice.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration

/**
 * The SecurityConfig class configures how requests to your application are secured.
 * The authorizeHttpRequests configuration defines that any request to /admin/\** must be authenticated (i.e., must pass the API key check).
 * All other requests are permitted.
 */
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val apiKeyService: ApiKeyService,
) {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.addFilterBefore(
                apiKeyAuthFilter(authenticationManager(authenticationConfiguration())),
                UsernamePasswordAuthenticationFilter::class.java,
            ).authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/admin/**")
                    .authenticated()
                    .anyRequest()
                    .permitAll()
            }

        return http.build()
    }

    @Bean
    fun apiKeyAuthFilter(authenticationManager: AuthenticationManager): ApiKeyAuthFilter {
        return ApiKeyAuthFilter(apiKeyService, authenticationManager)
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun authenticationConfiguration(): AuthenticationConfiguration {
        return AuthenticationConfiguration()
    }
}
