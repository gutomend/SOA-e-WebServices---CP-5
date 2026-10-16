# AutoEscola 3ESPG — Checkpoint 5

API REST desenvolvida em Java e Spring Boot para gerenciamento de uma autoescola, contemplando alunos, instrutores, usuários e instruções práticas.

Projeto acadêmico desenvolvido para a disciplina de **Arquitetura Orientada a Serviços (SOA) e Web Services**, do curso de Engenharia de Software da FIAP.

## 1. Objetivo

O projeto tem como objetivo implementar uma API REST com autenticação, persistência de dados, validações e regras de negócio relacionadas ao funcionamento de uma autoescola.

No Checkpoint 5, a aplicação foi evoluída com quatro recursos adicionais:

- Integração com a API externa ViaCEP;
- Documentação interativa com Swagger/OpenAPI;
- Configuração de CORS;
- Testes automatizados utilizando JUnit 5 e Mockito.

## 2. Tecnologias utilizadas

- Java 23
- Spring Boot 4.0.5
- Maven
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- MySQL
- Flyway
- Bean Validation
- Lombok
- Swagger/OpenAPI (Springdoc)
- JUnit 5
- Mockito
- API ViaCEP

## 3. Funcionalidades

### Gerenciamento de alunos

- Cadastro de alunos;
- Listagem e consulta por identificador;
- Atualização de dados;
- Exclusão lógica;
- Validação de informações cadastrais.

### Gerenciamento de instrutores

- Cadastro de instrutores;
- Listagem e consulta por identificador;
- Atualização de dados;
- Exclusão lógica;
- Validação de informações e disponibilidade.

### Gerenciamento de instruções

- Agendamento de instruções práticas;
- Associação entre alunos e instrutores;
- Seleção automática de instrutor;
- Validação de horários e conflitos;
- Cancelamento mediante regras de antecedência;
- Registro do motivo de cancelamento.

### Segurança

- Autenticação de usuários;
- Geração e validação de tokens JWT;
- Controle de acesso por perfis;
- Proteção dos endpoints conforme configuração do Spring Security.

## 4. Integração com a API ViaCEP

O Checkpoint 5 incorpora o consumo de uma API REST externa para consulta de endereços a partir do CEP.

**Serviço utilizado:** https://viacep.com.br/

A integração utiliza o `RestClient` do Spring para realizar requisições HTTP e retornar os dados de endereço.

### Endpoint

`GET /enderecos/cep/{cep}`

Exemplo de requisição:

```http
GET http://localhost:8085/enderecos/cep/01001000
```

A resposta contém os dados de endereço disponibilizados pelo ViaCEP, incluindo CEP, logradouro, bairro, localidade e UF.

O serviço também possui tratamento de erros para consultas malsucedidas.

## 5. Documentação Swagger/OpenAPI

A aplicação utiliza Springdoc OpenAPI para gerar documentação interativa dos endpoints.

Com a aplicação em execução, acesse:

**Swagger UI:** http://localhost:8085/swagger-ui/index.html

**Especificação OpenAPI:** http://localhost:8085/v3/api-docs

A documentação permite consultar os endpoints, visualizar os contratos das requisições e testar operações diretamente pelo navegador.

Os endpoints protegidos podem exigir autenticação JWT.

## 6. Configuração de CORS

O projeto possui uma configuração de Cross-Origin Resource Sharing (CORS), permitindo requisições de aplicações frontend autorizadas.

Origens configuradas:

- `http://localhost:3000`
- `http://localhost:5173`

Métodos HTTP permitidos:

- GET
- POST
- PUT
- PATCH
- DELETE
- OPTIONS

A configuração contempla os cabeçalhos necessários para requisições com autenticação, incluindo `Authorization` e `Content-Type`.

## 7. Principais endpoints

| Recurso | Método | Endpoint |
|---|---|---|
| Autenticação | POST | `/login` |
| Alunos | POST | `/alunos` |
| Alunos | GET | `/alunos` |
| Alunos | GET | `/alunos/{id}` |
| Alunos | PUT | `/alunos` |
| Alunos | DELETE | `/alunos/{id}` |
| Instrutores | POST | `/instrutores` |
| Instrutores | GET | `/instrutores` |
| Instrutores | GET | `/instrutores/{id}` |
| Instrutores | PUT | `/instrutores` |
| Instrutores | DELETE | `/instrutores/{id}` |
| Instruções | POST | `/instrucoes` |
| Instruções | PUT | `/instrucoes/{id}/cancelamento` |
| Consulta de CEP | GET | `/enderecos/cep/{cep}` |
| Verificação da API | GET | `/health-check` |

As requisições de cadastro e atualização devem respeitar os contratos e validações definidos pelos DTOs da aplicação.

## 8. Testes automatizados

Foram implementados testes utilizando JUnit 5 e Mockito para verificar comportamentos e regras de negócio das entidades e serviços.

### Classes de teste

**AlunoServiceTest**

- Verifica a exclusão lógica de alunos;
- Verifica o tratamento de alunos inexistentes.

**InstrutorServiceTest**

- Verifica a exclusão lógica de instrutores;
- Verifica o tratamento de instrutores inexistentes.

**UsuarioTest**

- Verifica os dados utilizados na autenticação;
- Verifica as permissões e o estado do usuário.

**InstrucaoServiceTest**

- Verifica o cancelamento de instruções dentro do prazo permitido;
- Verifica o impedimento de cancelamento com antecedência insuficiente.

### Executar os testes

No Windows, execute:

```powershell
.\mvnw.cmd test
```

Para executar apenas os quatro conjuntos de testes:

```powershell
.\mvnw.cmd "-Dtest=AlunoServiceTest,InstrutorServiceTest,UsuarioTest,InstrucaoServiceTest" test
```

Para compilar os testes sem executá-los:

```powershell
.\mvnw.cmd clean test-compile
```

Os testes unitários com Mockito permitem verificar as regras de negócio sem depender diretamente da execução de consultas reais no banco de dados.

## 9. Banco de dados

A aplicação utiliza MySQL para persistência dos dados.

Crie o banco de dados:

```sql
CREATE DATABASE autoescola3espg;
```

Configure o arquivo `src/main/resources/application.properties` com os parâmetros adequados ao ambiente:

```properties
spring.application.name=AutoEscola3ESPG
server.port=8085

spring.datasource.url=jdbc:mysql://localhost:3306/autoescola3espg
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD}

api.security.token.secret=${JWT_SECRET}
```

Defina as variáveis de ambiente `DB_PASSWORD` e `JWT_SECRET` antes de iniciar a aplicação. Não publique credenciais reais no GitHub.

### Migrations

O projeto utiliza Flyway para versionamento do banco de dados, incluindo a criação e evolução das tabelas de instrutores, alunos, usuários e instruções.

As migrations são aplicadas durante a inicialização da aplicação.

## 10. Executando o projeto

### Pré-requisitos

- JDK 23;
- MySQL instalado e em execução;
- Banco de dados criado;
- Variáveis de ambiente configuradas;
- IntelliJ IDEA ou outra IDE compatível.

O Maven Wrapper está incluído no projeto, dispensando uma instalação separada do Maven.

### Executar pelo terminal

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

A aplicação estará disponível em:

http://localhost:8085

### Gerar o pacote JAR

```powershell
.\mvnw.cmd clean package
```

O arquivo gerado ficará no diretório `target`.

Para executá-lo:

```powershell
java -jar target/AutoEscola3ESPG-0.0.1-SNAPSHOT.jar
```

## 11. Estrutura do projeto

```text
src/
├── main/
│   ├── java/br/com/fiap3espg/autoescola3espg/
│   │   ├── controller/
│   │   ├── domain/
│   │   │   ├── aluno/
│   │   │   ├── endereco/
│   │   │   ├── instrucao/
│   │   │   ├── instrutor/
│   │   │   └── usuario/
│   │   ├── infra/
│   │   │   ├── config/
│   │   │   ├── exception/
│   │   │   └── security/
│   │   ├── service/
│   │   └── AutoEscola3EspgApplication.java
│   └── resources/
│       ├── db/migration/
│       └── application.properties
└── test/
    └── java/br/com/fiap3espg/autoescola3espg/
        ├── domain/usuario/UsuarioTest.java
        └── service/
            ├── AlunoServiceTest.java
            ├── InstrutorServiceTest.java
            └── InstrucaoServiceTest.java
```

## 12. Equipe

**FIAP — Engenharia de Software — Turma 3ESPG**

| Integrante | RM |
|---|---|
| Augusto Mendonça | RM558371 |
| Gabriel Vasquez | RM557056 |
| Gustavo Oliveira | RM559163 |

**Disciplina:** Arquitetura Orientada a Serviços (SOA) e Web Services

**Professor:** Carlos Eduardo Machado de Oliveira

## 13. Considerações finais

O Checkpoint 5 representa a evolução da API AutoEscola 3ESPG, preservando as funcionalidades desenvolvidas anteriormente e acrescentando recursos importantes para integração e qualidade de software.

A integração com ViaCEP demonstra o consumo de serviços externos. A documentação Swagger/OpenAPI facilita a utilização e exploração da API. A configuração de CORS possibilita a comunicação com aplicações frontend autorizadas. Por fim, os testes automatizados contribuem para a verificação das regras de negócio e a manutenção do sistema.

O projeto reúne conceitos de arquitetura REST, segurança, persistência, integração entre sistemas, documentação e testes automatizados.
