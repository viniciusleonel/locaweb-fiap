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
  "password": "123"
}
```

- Validações:
```json
{
  "email": "Email is mandatory",
  "email": "Invalid email format",
  "username": "Username is mandatory",
  "password": "Password is mandatory"
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


### Preferences

#### Cadastrar preferências: POST http://localhost:8080/api/preferences

- Formato Json
```json
{
  "theme": "dark",
  "color_scheme": "dark-purple",
  "categories": "stared",
  "labels": "purple",
  "user_id": 3
}
```

- Validações:
```json
{
  "user_id": "User ID is mandatory"
}
```

#### Buscar preferência: GET http://localhost:8080/api/preferences/{id}
- Será retornado um único usuário.

#### Atualizar preferência: PUT http://localhost:8080/api/preferences/{id}
- Será atualizado somente os campos que não estiverem vazios.

#### Deletar preferência: DELETE http://localhost:8080/api/preferences/{id}
- Será deletado um único usuário.

Ao adicionar uma prefência ela será atrelada ao usuário automaticamente cujo id foi informado.

## Tecnologias Utilizadas

- Kotlin
- Spring Framework
- Hibernate (JPA)
- MySQL
- Docker