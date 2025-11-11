# Tech Challenge Gateway - API com Spring Security e JWT

## 📋 Descrição

API Gateway desenvolvida com Spring Boot 3.5.7, Spring Security e JWT (JSON Web Tokens) para autenticação e autorização de usuários no sistema de saúde.

## 🔒 Segurança

### Autenticação
- Sistema de autenticação baseado em JWT
- Tokens com validade de 24 horas
- Senha criptografada com BCrypt

### Autorização (Roles)
O sistema possui 4 tipos de usuário:

- **ADMIN**: Acesso completo ao sistema
- **MEDICO**: Acesso de leitura aos usuários
- **ENFERMEIRO**: Acesso de leitura aos usuários
- **PACIENTE**: Acesso básico (apenas autenticado)

## 🚀 Como Executar

### Pré-requisitos
- Java 21
- Maven 3.x

### Instalação e Execução

1. Clone o repositório
2. Navegue até o diretório do projeto
3. Execute o comando:

```bash
mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📡 Endpoints da API

### Autenticação (Públicos)

#### Registrar Novo Usuário
```http
POST /api/auth/registrar
Content-Type: application/json

{
  "nome": "João Silva",
  "documento": "12345678900",
  "email": "joao@email.com",
  "senha": "senha123",
  "tipoUsuario": "PACIENTE"
}
```

**Tipos de Usuário:** `ADMIN`, `MEDICO`, `ENFERMEIRO`, `PACIENTE`

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "id": 1,
  "email": "joao@email.com",
  "nome": "João Silva",
  "role": "PACIENTE"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "joao@email.com",
  "senha": "senha123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "id": 1,
  "email": "joao@email.com",
  "nome": "João Silva",
  "role": "PACIENTE"
}
```

### Gerenciamento de Usuários (Protegidos)

> **Nota:** Todos os endpoints abaixo requerem o header de autorização:
> ```
> Authorization: Bearer {seu_token_jwt}
> ```

#### Listar Todos os Usuários
```http
GET /api/usuarios
```
**Permissões:** ADMIN, MEDICO, ENFERMEIRO

**Resposta:**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "documento": "12345678900",
    "email": "joao@email.com",
    "tipoUsuario": "PACIENTE"
  }
]
```

#### Buscar Usuário por ID
```http
GET /api/usuarios/{id}
```
**Permissões:** ADMIN, MEDICO, ENFERMEIRO

#### Deletar Usuário
```http
DELETE /api/usuarios/{id}
```
**Permissões:** ADMIN

### Endpoints de Teste

#### Teste de Autenticação
```http
GET /api/test/authenticated
```
**Permissões:** Qualquer usuário autenticado

#### Teste ADMIN
```http
GET /api/test/admin
```
**Permissões:** ADMIN

#### Teste MEDICO
```http
GET /api/test/medico
```
**Permissões:** MEDICO

## 🗄️ Banco de Dados

O projeto utiliza H2 Database em memória para desenvolvimento.

### Console H2
- **URL:** http://localhost:8080/h2-console
- **JDBC URL:** jdbc:h2:mem:techchallenge
- **Username:** sa
- **Password:** (deixe em branco)

## 🔐 Configurações de Segurança

### application.properties

```properties
# JWT Configuration
jwt.secret=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
jwt.expiration=86400000  # 24 horas em milissegundos
```

## 📁 Estrutura do Projeto

```
src/main/java/br/com/fiap/techchallenge_gateway/
├── domain/
│   ├── controller/
│   │   └── UserController.java          # Endpoints REST
│   ├── dto/
│   │   ├── AuthResponse.java            # Response de autenticação
│   │   ├── LoginRequest.java            # Request de login
│   │   ├── RegisterRequest.java         # Request de registro
│   │   └── UsuarioResponse.java         # Response de usuário
│   ├── entity/
│   │   ├── Usuario.java                 # Entidade JPA
│   │   └── utils/
│   │       └── TipoUsuario.java         # Enum de roles
│   ├── repository/
│   │   └── UsuarioRepository.java       # Repositório JPA
│   └── service/
│       └── UsuarioService.java          # Lógica de negócio
└── security/
    ├── config/
    │   └── SecurityConfig.java          # Configuração Spring Security
    ├── exception/
    │   └── GlobalExceptionHandler.java  # Tratamento de exceções
    ├── jwt/
    │   ├── JwtAuthenticationFilter.java # Filtro JWT
    │   └── JwtTokenProvider.java        # Provider JWT
    └── service/
        └── CustomUserDetailsService.java # Service UserDetails
```

## 🧪 Testando a API

### Exemplo com cURL

1. **Registrar um ADMIN:**
```bash
curl -X POST http://localhost:8080/api/auth/registrar \
  -H "Content-Type: application/json" \
  -d "{\"nome\":\"Admin User\",\"documento\":\"11111111111\",\"email\":\"admin@email.com\",\"senha\":\"admin123\",\"tipoUsuario\":\"ADMIN\"}"
```

2. **Fazer Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"admin@email.com\",\"senha\":\"admin123\"}"
```

3. **Listar Usuários (com token):**
```bash
curl -X GET http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

### Exemplo com Postman/Insomnia

1. Crie uma requisição POST para `/api/auth/registrar`
2. No corpo da requisição, adicione o JSON com os dados do usuário
3. Copie o token retornado
4. Em requisições protegidas, adicione o header:
   - **Key:** `Authorization`
   - **Value:** `Bearer {seu_token}`

## 🛡️ Segurança Implementada

### Features
✅ Autenticação JWT stateless  
✅ Criptografia de senhas com BCrypt  
✅ Autorização baseada em roles (RBAC)  
✅ Tokens com expiração configurável  
✅ CSRF desabilitado para API REST  
✅ Sessões stateless  
✅ Validação de entrada com Bean Validation  
✅ Tratamento global de exceções  
✅ Proteção de endpoints por método HTTP e role  

### Regras de Acesso

| Endpoint | ADMIN | MEDICO | ENFERMEIRO | PACIENTE |
|----------|-------|--------|------------|----------|
| POST /api/auth/registrar | ✅ | ✅ | ✅ | ✅ |
| POST /api/auth/login | ✅ | ✅ | ✅ | ✅ |
| GET /api/usuarios | ✅ | ✅ | ✅ | ❌ |
| GET /api/usuarios/{id} | ✅ | ✅ | ✅ | ❌ |
| DELETE /api/usuarios/{id} | ✅ | ❌ | ❌ | ❌ |
| GET /api/test/* | ✅ | ✅ | ✅ | ✅ |

## 📝 Notas Importantes

- O token JWT deve ser incluído em todas as requisições protegidas
- O token expira após 24 horas
- As senhas são armazenadas criptografadas no banco de dados
- O banco H2 é resetado a cada reinicialização da aplicação
- Para produção, substitua o H2 por um banco de dados persistente (PostgreSQL, MySQL, etc.)
- Altere o `jwt.secret` para um valor seguro em produção

## 👨‍💻 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.5.7
- Spring Security
- Spring Data JPA
- JWT (jjwt 0.12.3)
- H2 Database
- Lombok
- Bean Validation
- Maven

## 📄 Licença

Projeto desenvolvido para o Tech Challenge - FIAP Fase 3

