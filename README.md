# PortariaApp

API REST para gerenciamento de portaria de condomínio, desenvolvida com Spring Boot. O sistema permite controlar encomendas recebidas na portaria, moradores, unidades e usuários (porteiros/administradores).

---

## Tecnologias

- **Java 21**
- **Spring Boot 4.0.5**
- **Spring Data JPA**
- **Spring Web MVC**
- **MySQL**
- **Flyway** (migrations)
- **Lombok**
- **Jakarta Validation**
- **H2** (testes)

---

## Estrutura do Domínio

```
User        → usuário do sistema (ADMIN ou PORTEIRO)
Unit        → unidade / apartamento do condomínio
Resident    → morador que reside em uma unidade
Package     → encomenda recebida na portaria
```

**Relacionamentos:**
- `Resident` pertence a uma `Unit`
- `Package` pertence a um `Resident`
- `Package` registra qual `User` recebeu e qual `User` entregou a encomenda

---

## Pré-requisitos

- Java 21+
- Maven 3.8+
- MySQL 8+

---

## Configuração

1. Crie o banco de dados MySQL:

```sql
CREATE DATABASE portaria_db;
```

2. Ajuste as credenciais em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/portaria_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
```

As tabelas serão criadas automaticamente pelo Flyway ao iniciar a aplicação.

---

## Como executar

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

---

## Endpoints

### Usuários — `/v1/user`

| Método | Endpoint       | Descrição                    |
|--------|----------------|------------------------------|
| POST   | `/v1/user`     | Criar usuário                |
| GET    | `/v1/user`     | Listar todos os usuários     |
| GET    | `/v1/user/{name}` | Buscar usuário por nome   |
| PATCH  | `/v1/user/{id}` | Atualizar usuário parcialmente |
| DELETE | `/v1/user/{name}` | Deletar usuário por nome  |

### Unidades — `/v1/unit`

| Método | Endpoint            | Descrição               |
|--------|---------------------|-------------------------|
| POST   | `/v1/unit`          | Criar unidade           |
| GET    | `/v1/unit`          | Listar todas as unidades|
| GET    | `/v1/unit/{number}` | Buscar unidade por número|
| PUT    | `/v1/unit/{id}`     | Atualizar unidade       |
| DELETE | `/v1/unit/{id}`     | Deletar unidade         |

### Moradores — `/v1/resident`

| Método | Endpoint                      | Descrição                        |
|--------|-------------------------------|----------------------------------|
| POST   | `/v1/resident`                | Cadastrar morador                |
| GET    | `/v1/resident`                | Listar todos os moradores        |
| GET    | `/v1/resident/{id}`           | Buscar morador por ID            |
| GET    | `/v1/resident/unit/{unitId}`  | Listar moradores por unidade     |
| GET    | `/v1/resident/search?name=`   | Buscar moradores por nome        |
| PUT    | `/v1/resident/{id}`           | Atualizar morador                |
| PATCH  | `/v1/resident/deactivate/{id}`| Desativar morador                |

### Encomendas — `/v1/package`

| Método | Endpoint                   | Descrição                        |
|--------|----------------------------|----------------------------------|
| POST   | `/v1/package`              | Registrar encomenda recebida     |
| GET    | `/v1/package`              | Listar todas as encomendas       |
| GET    | `/v1/package/{id}`         | Buscar encomenda por ID          |
| PATCH  | `/v1/package/{id}/pickup`  | Registrar retirada de encomenda  |
| PATCH  | `/v1/package/{id}/cancel`  | Cancelar encomenda               |

---

## Ciclo de vida da encomenda

```
RECEIVED → STORED → PICKED_UP
                 └→ CANCELED
```

| Status     | Descrição                              |
|------------|----------------------------------------|
| RECEIVED   | Encomenda recebida na portaria         |
| STORED     | Encomenda armazenada aguardando retirada|
| PICKED_UP  | Encomenda retirada pelo morador        |
| CANCELED   | Encomenda cancelada                    |

**Regras principais:**
- Uma encomenda não pode ser retirada se já estiver com status `PICKED_UP` ou `CANCELED`.
- Uma encomenda não pode ser cancelada após já ter sido retirada (`PICKED_UP`).
- Encomendas não são deletadas fisicamente — o cancelamento altera o status para `CANCELED`.

---

## Testes

```bash
./mvnw test
```

Os testes utilizam banco H2 em memória (configurado em `src/test/resources/application.properties`).

---

## Estrutura do projeto

```
src/
├── main/
│   ├── java/com/murilo/portariaApp/
│   │   ├── controller/     # Controllers REST
│   │   ├── dto/            # DTOs de request e response
│   │   ├── entity/         # Entidades JPA
│   │   ├── enums/          # Enums (PackageStatus, Role)
│   │   ├── exception/      # Exceções de negócio
│   │   ├── repository/     # Repositórios Spring Data
│   │   └── service/        # Regras de negócio
│   └── resources/
│       ├── application.properties
│       └── db/migration/   # Scripts Flyway (V1–V4)
└── test/
    └── java/com/murilo/portariaApp/
```
