# PortariaApp - Documentação Técnica

## 📋 Visão Geral

**PortariaApp** é uma aplicação Spring Boot para gerenciar operações de portaria em condomínios. O sistema permite o registro, rastreamento e entrega de pacotes, além de gerenciar usuários, residentes e unidades do condomínio.

- **Stack**: Java 21, Spring Boot 4.0.5, MySQL, Flyway
- **Arquitetura**: Layered (Controller → Service → Repository)
- **Padrões**: DTOs, Enums, Transações, JPA/Hibernate
- **Porta**: 8080

---

## 🏗️ Arquitetura do Projeto

```
src/main/
├── java/com/murilo/portariaApp/
│   ├── Entity/              # Entidades JPA
│   ├── controller/          # Endpoints REST
│   ├── service/             # Lógica de negócio
│   ├── repository/          # Acesso a dados (JPA)
│   ├── dto/                 # Data Transfer Objects
│   ├── exception/           # Exceções customizadas
│   ├── enums/               # Enumerações
│   └── PortariaAppApplication.java  # Bootstrap
└── resources/
    ├── application.properties
    └── db/migration/        # Scripts SQL (Flyway)
```

---

## 📊 Entidades Principais

### 1. **User** (Usuários)
```java
- id: UUID (PK)
- name: String
- email: String (unique)
- password: String
- role: Enum [ADMIN, PORTEIRO, MORADOR]
- active: Boolean
```
**Uso**: Usuários do sistema (administradores, porteiros).

### 2. **Resident** (Moradores)
```java
- id: UUID (PK)
- name: String (150 chars)
- phone: String (20 chars)
- email: String (unique, 150 chars)
- active: Boolean
- unit_id: FK → Unit
```
**Uso**: Registra moradores do condomínio e associa a unidades.

### 3. **Unit** (Unidades)
```java
- id: UUID (PK)
- block: String
- number: String
- floor: Integer
- description: String
```
**Uso**: Representa apartamentos/unidades do condomínio.

### 4. **Package** (Pacotes)
```java
- id: UUID (PK)
- description: String (150 chars)
- sender: String (150 chars)
- tracking_code: String (unique, 100 chars)
- received_at: LocalDateTime
- picked_up_at: LocalDateTime (nullable)
- status: Enum [RECEIVED, STORED, PICKED_UP, CANCELED]
- notes: String (255 chars, nullable)
- resident_id: FK → Resident
- received_by: FK → User
- delivered_by: FK → User (nullable)
```
**Uso**: Rastreia pacotes recebidos para moradores.

---

## 🔑 Enumerações

### **Role** (Papéis de Usuário)
- `ADMIN`: Administrador com acesso total
- `PORTEIRO`: Porteiro que recebe/entrega pacotes
- `MORADOR`: Residente do condomínio

### **PackageStatus** (Status de Pacotes)
- `RECEIVED`: Pacote recebido na portaria
- `STORED`: Pacote armazenado/aguardando entrega
- `PICKED_UP`: Pacote retirado pelo morador
- `CANCELED`: Pacote cancelado

---

## 🛣️ Endpoints REST

### **Users** (`/v1/user`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/v1/user` | Criar novo usuário |
| GET | `/v1/user` | Listar todos os usuários |
| GET | `/v1/user/{name}` | Buscar usuário por nome |
| PATCH | `/v1/user/{id}` | Atualizar parcialmente usuário |
| DELETE | `/v1/user/{name}` | Deletar usuário por nome |

**Validações (UserRequestDTO)**:
- `name`: obrigatório
- `email`: obrigatório, formato válido
- `password`: obrigatório, mínimo 6 caracteres
- `role`: obrigatório
- `active`: opcional (default true)

### **Residents** (`/v1/resident`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/v1/resident` | Criar novo morador |
| GET | `/v1/resident` | Listar todos moradores |
| GET | `/v1/resident/{id}` | Buscar morador por ID |
| GET | `/v1/resident/unit/{unitId}` | Listar moradores de uma unidade |
| GET | `/v1/resident/search?name=X` | Buscar moradores por nome |
| PUT | `/v1/resident/{id}` | Atualizar morador completo |
| PATCH | `/v1/resident/deactivate/{id}` | Desativar morador |

### **Units** (`/v1/unit`)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/v1/unit` | Criar nova unidade |
| GET | `/v1/unit` | Listar todas unidades |
| GET | `/v1/unit/{number}` | Buscar unidades por número |
| PUT | `/v1/unit/{id}` | Atualizar unidade completa |
| DELETE | `/v1/unit/{id}` | Deletar unidade |

### **Packages** (`/v1/package`)
⚠️ **Status**: Endpoints não implementados (controller vazio)

---

## 🗄️ Banco de Dados

### Configuração (application.properties)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/portaria_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.locations=classpath:db/migration
server.port=8080
```

### Migrações (Flyway)
- **V1**: Tabela `users`
- **V2**: Tabela `units`
- **V3**: Tabela `residents`
- **V4**: Tabela `packages`

---

## 📦 Dependências Principais

```xml
<!-- Spring Boot 4.0.5 -->
spring-boot-starter-data-jpa
spring-boot-starter-webmvc
spring-boot-starter-flyway

<!-- Banco de Dados -->
mysql-connector-j
flyway-mysql

<!-- Utilidades -->
lombok
jakarta.validation-api (3.0.2)
```

---

## 🔧 Configuração Local

### Pré-requisitos
- Java 21+
- MySQL 8.0+
- Maven 3.8+

### Passos
1. **Clone o repositório**
   ```bash
   git clone <repo-url>
   cd PortariaApp
   ```

2. **Configure o MySQL**
   ```sql
   CREATE DATABASE portaria_db;
   ```

3. **Execute a aplicação**
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

4. **Verificar saúde**
   ```bash
   curl http://localhost:8080/v1/user
   ```

---

## ⚠️ Problemas Conhecidos & TODOs

### Implementação Incompleta
- ❌ **PackageController**: Controller vazio, sem endpoints implementados
- ❌ **PackageRequestDTO**: DTO vazio (sem campos definidos)
- ❌ **PackageService**: Service sem implementação de lógica

### Possíveis Melhorias
1. **Segurança**: Implementar JWT/OAuth2 para autenticação
2. **Validação**: Adicionar validações mais rigorosas em DTOs
3. **Tratamento de Exceções**: Handler global com `@ControllerAdvice`
4. **Logs**: Integrar SLF4J/Logback para melhor rastreamento
5. **Testes**: Adicionar testes unitários e integração
6. **API Documentation**: Swagger/SpringDoc OpenAPI

### Questões de Design
- ⚠️ **Password Storage**: Senhas armazenadas em texto plano (usar BCrypt)
- ⚠️ **Timestamps**: Tables users/residents usam CURRENT_TIMESTAMP; considerar auditoria
- ⚠️ **Soft Deletes**: Usar flag `active` ao invés de deletar dados

---

## 🚀 Próximos Passos Recomendados

1. **Completar Package Module**
   - Implementar `PackageRequestDTO` com validações
   - Implementar `PackageService` com CRUD
   - Implementar endpoints no `PackageController`

2. **Segurança**
   - Adicionar Spring Security
   - Hash de passwords com BCrypt
   - Implementar autenticação JWT

3. **Qualidade de Código**
   - Adicionar testes (JUnit 5, TestContainers)
   - Configurar linter (CheckStyle, SpotBugs)
   - Documentação com Swagger

4. **DevOps**
   - Docker para containerização
   - CI/CD pipeline (GitHub Actions)

---

## 📝 Convenções de Código

- **Naming**: `camelCase` para variáveis, `PascalCase` para classes
- **DTOs**: Usar records Java 16+ quando possível
- **Validação**: Usar `jakarta.validation` annotations
- **Transações**: Marcar métodos com `@Transactional`
- **Lombok**: Usar `@Getter`, `@Setter`, `@RequiredArgsConstructor`, `@Builder`

---

## 📞 Suporte

Para dúvidas ou bugs, abra uma issue no repositório do projeto.

**Última atualização**: 2026-04-09
