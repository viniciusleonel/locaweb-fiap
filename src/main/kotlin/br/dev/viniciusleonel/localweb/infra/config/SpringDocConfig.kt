package br.dev.viniciusleonel.localweb.infra.config


import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
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
                    .description(
                        "A API Locaweb é uma aplicação desenvolvida em Kotlin e Spring, projetada especificamente para o aplicativo da Locaweb, " +
                                "permite o envio de emails entre usuários, garantindo um controle rigoroso de acesso e leitura, assegurando que apenas " +
                                "usuários ativos e logados possam realizar essas operações. Além disso, os usuários podem se cadastrar, salvar suas " +
                                "preferências do aplicativo, como configurações personalizadas e temas, facilitando futuras migrações de dados.\n\n" +
                                "Foi implementado um workflow de Integração Contínua (CI) utilizando GitHub Actions para testes e builds em pull requests, " +
                                "e um processo de Entrega Contínua (CD) que realiza o deploy automático da aplicação em produção. A aplicação é containerizada " +
                                "com Docker e implantada na Azure, garantindo atualizações rápidas e escalabilidade."
                    )
                    .contact(
                        Contact()
                            .name("Vinicius Leonel")
                            .email("viniciuslps.cms@gmail.com")
                            .url("https://www.linkedin.com/in/viniciuslps/")
                    )
                    .license(
                        License()
                            .name("GitHub")
                            .url("https://github.com/viniciusleonel/traffic-incident-management-api")
                    )
            )
    }
}
