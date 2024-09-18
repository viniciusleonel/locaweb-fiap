# Locaweb API

A API Locaweb é uma solução robusta desenvolvida em Kotlin e Spring para gerenciar usuários, 
preferências de usuários e emails em um aplicativo Kotlin da Locaweb. A API oferece endpoints 
para operações CRUD (Create, Read, Update, Delete) em usuários e suas preferências, além de 
funcionalidades para envio e gerenciamento de emails.

Especificamente projetada para atender ao aplicativo da Locaweb, a API permite que os usuários 
se cadastrem, salvem suas preferências do aplicativo (como configurações personalizadas e temas), 
facilitando futuras migrações de dados. Além disso, ela permite o envio de emails entre usuários, 
com controle rigoroso de acesso e de leitura, garantindo que essas operações sejam realizadas 
apenas por usuários ativos e logados no sistema.

### Requisitos:
- Ter o Docker instalado.
- Ter o Java instalado.
- Clonar este repositório: `git clone https://github.com/viniciusleonel/locaweb-fiap`.
- Abra o projeto em sua IDE preferida (Indicamos o IntelliJ).

## Criando uma database com Docker 
1. Abra o terminal na pasta raiz do projeto.
2. Inicie o contêiner do banco de dados definido em `docker-compose.database.yml` com o comando:
   ```sh
   docker-compose -f docker-compose.database.yml up -d
   ``` 
3. Obtenha o ID do container criado ( `mysql-database-locaweb` ) com o comando:
    ```shell
    docker ps
    ```
4. Acesse o MySql dentro do container com o comando:
    ```shell
    docker exec -it CONTAINER_ID mysql -u root -p
    ```
   Substitua `CONTAINER_ID ` pelo ID obtido no passo 3.
5. Digite a senha `locaweb123` para acessar o MySql.
6. Crie a database com o comando:
    ```shell
    create database locaweb;
    ```
   
A database estará pronta para uso.

## Executando Locaweb API na IDE (INTELLIJ)
1. Com a database criada, vá para o arquivos `application.properties` e descomente o seguinte código:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/locaweb
spring.datasource.username=locaweb
spring.datasource.password=locaweb123
```

2. Agora execute a classe `LocawebApplication` e a API estará pronta para uso.

## Executando Locaweb API com Docker
1. Com a database criada, vá para o arquivos `application.properties` e descomente o seguinte código:
```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```

2. Construa a imagem Docker da aplicação executando o seguinte comando no terminal na pasta raiz do projeto:
   ```sh
   docker build -t locaweb-api .
   ```
3. Após a construção da imagem, inicie os contêineres definidos no `docker-compose.yml` com o comando:
   ```sh
   docker-compose up -d
   ```
   Isso irá iniciar os contêineres em segundo plano.

Após executar o comando `docker-compose up -d`, serão criados três contêineres: `locaweb`, `database-locaweb` e `api-locaweb`. 
O contêiner `database-locaweb` será responsável pelo banco de dados MySQL, enquanto o contêiner `api-locaweb` hospedará a aplicação 
Locaweb API. O contêiner `locaweb` servirá como uma rede para conectar os outros dois contêineres. A aplicação estará pronta para uso 
e poderá ser acessada através da URL base `http://localhost:8080/api`.

### Documentação SpringDoc ( Swagger ): http://localhost:8080/swagger-ui/index.html

## Como Utilizar

- Exemplo de requisições no arquivo `http`.

### Parâmetros de Paginação e Ordenação
  `?page=0&size=1&sort=field`
- **page**: <span style="color: green;">integer</span>  
  minimum: 0
- **size**: <span style="color: green;">integer</span>  
  minimum: 1
- **sort**: <span style="color: green;">[string]</span>

Exemplo: `http://localhost:8080/api/user?page=0&size=1&sort=name,desc`


### Users

#### Cadastrar usuário: POST http://localhost:8080/api/user

- Formato Json
```json
{
  "email": "teste@gmail.com",
  "username": "teste",
  "password": "123456"
}
```

- Sucesso ao cadastrar (Retorna User)
```json
{
  "id": 1,
  "name": "name",
  "email": "teste@gmail.com",
  "username": "teste",
  "password": "$2a$10$VO4VMk9JtrnJvvdLq3odnuZDugDdGuYUp3h9VmfKlc.mODYGoK.Hi",
  "isLoggedIn": false,
  "lastLogin": "2022-01-01T00:00:00",
  "status": true,
  "userPreferences": {
    "id": 1,
    "theme": "",
    "colorScheme": "",
    "categories": "",
    "labels": ""
  },
  "receivedEmails": [],
  "sentEmails": []
}
```

- Validações (Possíveis erros):
```json
{
  "name": "Name is required",
  "email": "Email is required",
  "email": "Invalid email format",
  "username": "Username is required",
  "password": "Password is required"
}
```

#### Login: POST http://localhost:8080/api/user/login
- Será verificado se o `username` existe e se `password` é a mesma que no banco de dados, se sim
  `isLoggedIn` será alterado para `true` e os dados do usuário será retornado.
```json
{
  "username": "teste",
  "password": "123456",
  "userPreferences": {
    "id": 3,
    "theme": "",
    "colorScheme": "",
    "categories": "",
    "labels": ""
  }
}
```

- Caso contrário, se o `username` não existir ou se a `password` estiver errada:
```json
{ "error": "User not found with username: 'tes'" }
```
```json
{ "error": "Invalid password" }
```

#### Listar usuários: GET http://localhost:8080/api/user
- Será retornada uma lista de usuários com paginação.

#### Buscar usuário: GET http://localhost:8080/api/user/{id}
- Necessário usuário estar logado!*
- Será retornado um único usuário.

#### Atualizar usuário: PUT http://localhost:8080/api/user/{id}
- Necessário usuário estar logado!*
- Será atualizado somente os campos que não estiverem vazios.

#### Deletar usuário: DELETE http://localhost:8080/api/user/{id}
- Necessário usuário estar logado!*
- Será deletado um único usuário.(Status será atribuído com o valor 'false')

#### Logout: POST http://localhost:8080/api/user/logout
- Necessário usuário estar logado!*
- Será verificado se o `username` existe no banco de dados, se sim
  `isLoggedIn` será alterado para `false`.
```json
{ "username": "teste" }
```
- Retorno:
```json
{ "message": "User 'teste' is logged out!" } 
```

- Se o usuário não estiver logado ou se não existir:
```json
{ "error": "User 'teste' is not logged in" }
```
```json
{ "error": "User not found with username: 'tes'" }
```

### Preferences - Necessário usuário estar logado!*

#### Buscar preferências de um usuário pelo ID: GET http://localhost:8080/api/preferences/user/{id}
- Necessário usuário estar logado!*

#### Atualizar preferência por ID: PUT http://localhost:8080/api/preferences/{id}
- Será atualizado somente os campos que não estiverem vazios.

#### Deletar preferência: DELETE http://localhost:8080/api/preferences/{id}
- Será deletado um único usuário.

As preferências de um usuário são criadas junto com o registro do mesmo, sendo inicialmente vazias.

### Emails - Usuário remetente necessário estar logado!*

- Controle de SPAM: 5 emails por MIN.
- Todos os campos são obrigatórios

#### Enviar Email: POST http://localhost:8080/api/email

- Formato Json 
```json
{
  "sentByUser": "user1@gmail.com",
  "receivedByUser": "user2@gmail.com",
  "subject": "Email Teste",
  "body": "Estou mandando um email teste"
}
```

- Validações:
```json
{
  "error": "User not found with id: 'tes@gmail.com'",
  "subject": "Subject is required.",
  "receivedByUser": "Recipient's email is required.",
  "subject": "Subject is required.",
  "body": "Email body is required."
}
```

- Retorno
```json
{
  "id": 1,
  "subject": "Email Teste",
  "sendTo": "user2@gmail.com",
  "sentAt": "2024-09-01T14:01:12.8877518",
  "wasRead": false
}
```

#### Buscar email: GET http://localhost:8080/api/email/{emailId}/user/{userId}
- Somente quem estiver presente no email (destinatário ou remetente) poderá buscá-lo 
- Será retornado um único email com `body`.
- Campo `wasRead` será alterado para `true`

```json
{
  "id": 1,
  "sendByUser": "user1@gmail.com",
  "receiveByUser": "user2@gmail.com",
  "subject": "Email Teste",
  "body": "Estou mandando um email teste",
  "sendAt": "2024-08-29T18:09:39.66685",
  "wasRead": true
}
```

#### Listar emails enviados pelo usuário: GET http://localhost:8080/api/email/sent/{id}?sort=sentAt,desc
- Será retornada uma lista de emails com paginação sem campo `body`.

#### Listar emails recebidos do usuário: GET http://localhost:8080/api/email/received/{id}?sort=sentAt,desc
- Será retornada uma lista de emails com paginação sem campo `body`.

#### Deletar email: DELETE http://localhost:8080/api/email/{id}
- Será deletado um único email.


Ao enviar um e-mail, a API irá adicionar ao usuário remetente e destinatário nas 
respectivas listas de e-mails enviados e recebidos. O usuário remetente será associado 
como o responsável pelo envio do e-mail, enquanto o destinatário será a pessoa que 
receberá o e-mail. Além disso, a API garantirá que o e-mail seja armazenado corretamente 
no banco de dados com todos os detalhes pertinentes, como o assunto, o corpo do e-mail 
e a data/hora de envio. A operação de envio também atualizará o status do e-mail, 
marcando-o como não lido para o destinatário até que seja visualizado.

## Tecnologias Utilizadas

- Kotlin
- Spring
- Hibernate (JPA)
- MySQL
- Docker