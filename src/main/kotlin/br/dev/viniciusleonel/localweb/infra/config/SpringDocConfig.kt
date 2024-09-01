package br.dev.viniciusleonel.localweb.infra.config


import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringDocConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Locaweb API")
                    .version("1.0")
                    .description("API para gerenciar usuários, preferências e emails")
            )
    }
}