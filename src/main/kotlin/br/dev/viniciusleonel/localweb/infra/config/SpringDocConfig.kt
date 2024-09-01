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
                    .description("A API Locaweb é uma solução robusta desenvolvida em Kotlin e Spring para gerenciar usuários, preferências de usuários e emails em um aplicativo Kotlin da Locaweb. A API oferece endpoints para operações CRUD (Create, Read, Update, Delete) em usuários e suas preferências, além de funcionalidades para envio e gerenciamento de emails.\n" +
                            "\n" +
                            "Especificamente projetada para atender ao aplicativo da Locaweb, a API permite que os usuários se cadastrem, salvem suas preferências do aplicativo (como configurações personalizadas e temas), facilitando futuras migrações de dados. Além disso, ela permite o envio de emails entre usuários, com controle rigoroso de acesso e de leitura, garantindo que essas operações sejam realizadas apenas por usuários ativos e logados no sistema.\n" +
                            "\nPara mais detalhes, acesse o [repositório no GitHub](https://github.com/viniciusleonel/locaweb-fiap).")

            )
    }
}