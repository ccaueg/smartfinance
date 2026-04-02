# SmartFinance

<p>API de controle de gastos com análise de comportamento financeiro</p>
<hr />

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0202?style=for-the-badge&logo=flyway&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23C1E1C1?style=for-the-badge&logo=swagger&logoColor=black)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

## Endpoints

### Autenticação

- `POST /auth/register` — cria um novo usuário
- `POST /auth/login` — autentica o usuário e retorna um token JWT

### Transações

Rotas protegidas por autenticação:

- `POST /transactions` — cria uma transação
- `GET /transactions` — lista as transações do usuário autenticado
- `GET /transactions/{id}` — busca uma transação por id
- `PUT /transactions/{id}` — atualiza uma transação
- `DELETE /transactions/{id}` — remove uma transação

## Segurança

A aplicação usa JWT para autenticação stateless.

- `/auth/**` e a documentação Swagger estão liberadas
- todas as outras rotas exigem token válido
- o token é gerado no login e enviado no cabeçalho `Authorization: Bearer <token>`

## TODO

- [ ] CRUD de categorias via API
- [ ] relatórios e análises financeiras
- [ ] filtros por período, tipo e categoria
- [ ] testes para regras de negócio e integração com banco
- [ ] documentação de exemplos de requisição e resposta

## Estrutura do Projeto

```
src/
├───main
│   ├───java/dev/caue/smartfinance
│   │   ├───config
│   │   ├───controller
│   │   ├───domain
│   │   │   ├───category
│   │   │   ├───transaction
│   │   │   └───user
│   │   ├───dto
│   │   ├───exception
│   │   ├───mapper
│   │   ├───repository
│   │   ├───security
│   │   └───service
│   └───resources
│       ├───db
│       │   └───migration
│       ├───static
│       └───templates
└───test
```

---

### Conecte-se comigo
[![LinkedIn](https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/caue-gomes)
