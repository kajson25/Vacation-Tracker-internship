package group.employeeservice.documentation

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {
    @Bean
    fun publicApi(): GroupedOpenApi =
        GroupedOpenApi
            .builder()
            .group("public")
            .packagesToScan("group.employeeservice.controller")
            .build()

    @Bean
    fun customOpenAPI(): OpenAPI =
        OpenAPI()
            .info(
                Info()
                    .title("API")
                    .description("API swagger definition")
                    .version("1.0.0")
                    .termsOfService("Terms of service")
                    .contact(
                        Contact()
                            .name("Katarina Vucicevic")
                            .email("katarina.vucicevic@rbt.interns.rs"),
                    ).license(License().name("").url("")),
            )
}
