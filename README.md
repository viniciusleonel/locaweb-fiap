# Locaweb API

Decrição


## Executando o banco de dados MySql com Docker

1. Tenha o Docker instalado.
2. Clone este repositório: `git clone https://github.com/viniciusleonel/locaweb-fiap`
3. Abra o projeto em sua IDE preferida.
4. Existe uma configuração para um banco de dados local em `application.properties` e `docker-compose.yml`,
caso deseje usar uma database própria, faça a configuração nesses arquivos.
6. Abra o terminal na pasta raiz que contém o arquivo `docker-compose.yml` e digite o comando:
`docker-compose up -d`

Feito isso, o banco de dados estará pronto, agora inicie a aplicação executando a classe `LocalwebApplication`.

URL Base: `http://localhost:8080/api`.

## Como Utilizar

  ### Parâmetros de Paginação e Ordenação
  /pacientes?page=0&size=1&sort=nome
- **page**: <span style="color: green;">integer</span>  
  minimum: 0
- **size**: <span style="color: green;">integer</span>  
  minimum: 1
- **sort**: <span style="color: green;">[string]</span>


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
  "receivedEmails": [],
  "sentEmails": [],
  "userPreferences": []
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

#### Listar usuários: GET http://localhost:8080/api/user
- Será retornada uma lista de usuários com paginação.

#### Buscar usuário: GET http://localhost:8080/api/user/{id}
- Será retornado um único usuário.

#### Atualizar usuário: PUT http://localhost:8080/api/user/{id}
- Será atualizado somente os campos que não estiverem vazios.

#### Deletar usuário: DELETE http://localhost:8080/api/user/{id}
- Será deletado um único usuário.

#### Login: POST http://localhost:8080/api/user/login
- Será verificado se o `username` existe e se `password` é a mesma que no banco de dados, se sim
  `isLoggedIn` será alterado para `true` e os dados do usuário será retornado.
```json
{
  "username": "teste",
  "password": "123456"
}
```

- Caso contrário, se o `username` não existir ou se a `password` estiver errada:
```json
{ "error": "User not found with username: 'tes'" }
```
```json
{ "error": "Invalid password" }
```


#### Login: POST http://localhost:8080/api/user/logout
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

### Preferences

#### Cadastrar preferências: POST http://localhost:8080/api/preferences

- Formato Json
```json
{
  "theme": "dark",
  "colorScheme": "dark-purple",
  "categories": "stared",
  "labels": "purple",
  "userId": 3
}
```

- Validações:
```json
{
  "userId": "User ID is required"
}
```

#### Buscar preferência: GET http://localhost:8080/api/preferences/{id}
- Será retornado um único usuário.

#### Atualizar preferência: PUT http://localhost:8080/api/preferences/{id}
- Será atualizado somente os campos que não estiverem vazios.

#### Deletar preferência: DELETE http://localhost:8080/api/preferences/{id}
- Será deletado um único usuário.

Ao adicionar uma prefência ela será atrelada ao usuário automaticamente cujo id foi informado.


- Retorno de um User com Preferências adicionadas a ele.
```json
{
  "id": 1,
  "name": "Name",
  "email": "teste@gmail.com",
  "username": "teste",
  "password": "$2a$10$VO4VMk9JtrnJvvdLq3odnuZDugDdGuYUp3h9VmfKlc.mODYGoK.Hi",
  "isLoggedIn": true,
  "receivedEmails": [],
  "sentEmails": [],
  "userPreferences": [
    {
      "id": 1,
      "theme": "dark",
      "colorScheme": "dark-purple",
      "categories": "stared",
      "labels": "purple"
    }
  ]
}
```

### Emails

#### Enviar Email: POST http://localhost:8080/api/email

- Formato Json - Todos os campos são obrigatórios
```json
{
  "sentByUser": "teste@gmail.com",
  "receivedByUser": "vinicius@gmail.com",
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
  "id": 11,
  "subject": "Email Teste",
  "body": "Estou mandando um email teste",
  "sentAt": "2024-08-29T17:43:49.0523626",
  "wasRead": false
}
```

#### Buscar email: GET http://localhost:8080/api/email/{id}
- Será retornado um único email com 'body'.

```json
{
  "id": 1,
  "sendByUser": "teste",
  "receiveByUser": "vinicim",
  "subject": "Email Teste",
  "body": "Estou mandando um email teste",
  "sendAt": "2024-08-29T18:09:39.66685",
  "wasRead": false
}
```

#### Listar emails: GET http://localhost:8080/api/email
- Será retornada uma lista de emails com paginação sem campo 'body'.

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
- Spring Framework
- Hibernate (JPA)
- MySQL
- Docker