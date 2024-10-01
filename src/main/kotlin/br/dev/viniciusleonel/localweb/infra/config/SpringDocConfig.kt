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
                    .description("""A API Locaweb é uma solução robusta desenvolvida em Kotlin e Spring para gerenciar usuários, preferências de usuários e emails em um aplicativo Kotlin da Locaweb. A API oferece endpoints para operações CRUD (Create, Read, Update, Delete) em usuários e suas preferências, além de funcionalidades para envio e gerenciamento de emails.
                            
                            Especificamente projetada para atender ao aplicativo da Locaweb, a API permite que os usuários se cadastrem, salvem suas preferências do aplicativo (como configurações personalizadas e temas), facilitando futuras migrações de dados. Além disso, ela permite o envio de emails entre usuários, com controle rigoroso de acesso e de leitura, garantindo que essas operações sejam realizadas apenas por usuários ativos e logados no sistema.
                            
                            Foi implementado um workflow de Integração Contínua (CI) utilizando GitHub Actions para testes e builds em pull requests, e um processo de Entrega Contínua (CD) que realiza o deploy automático da aplicação em produção. A aplicação é containerizada com Docker e implantada na Azure, garantindo atualizações rápidas e escalabilidade.
                            
                            Para mais detalhes, acesse o [repositório no GitHub](https://github.com/viniciusleonel/locaweb-fiap).
                            
                            [LinkedIn](https://www.linkedin.com/in/viniciuslps/).
                            """)

            )
    }
}