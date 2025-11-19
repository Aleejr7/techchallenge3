# Tech Challenge 3 - Sistema de Agendamento Médico
Projeto FIAP - Fase 3

## Descrição
Sistema de agendamento médico com autenticação JWT e controle de acesso baseado em roles (MEDICO, ENFERMEIRO, PACIENTE).

## Arquitetura
O projeto é composto por:
- **app-service-auth**: Serviço de autenticação e gerenciamento de usuários
- **app-service-agendamento**: Serviço de agendamento de consultas
- **Kong API Gateway**: Gateway para roteamento e validação JWT
- **MariaDB**: Banco de dados compartilhado entre os serviços

## Tecnologias
- Java 21 (LTS)
- Spring Boot 3.5.7
- MariaDB 10.6
- Kong Gateway 3.4
- Docker & Docker Compose
- JWT (JSON Web Token)

## Estrutura de Roles

### PACIENTE
- Pode visualizar apenas suas próprias consultas
- **Não pode** criar ou editar consultas

### MEDICO
- Pode criar consultas
- Pode visualizar apenas consultas onde é o médico responsável
- Pode editar apenas suas próprias consultas

### ENFERMEIRO
- Pode criar consultas
- Pode visualizar todas as consultas
- Pode editar todas as consultas

## Banco de Dados
Ambos os serviços utilizam o mesmo banco MariaDB com as seguintes tabelas:

### Tabela `usuarios`
- id
- nome
- documento
- email
- senha
- tipo_usuario (MEDICO, ENFERMEIRO, PACIENTE)

### Tabela `consulta`
- id
- id_medico
- id_paciente
- descricao
- dia_hora_consulta
- status (ABERTO, ANDAMENTO, FINALIZADA, CANCELADA)
- motivo_consulta

## API Gateway - Kong

### Rotas Públicas (sem JWT)
- `POST /auth/login` - Login de usuário
- `POST /auth/register` - Registro de novo usuário

### Rotas Protegidas (requerem JWT)
- `GET /consulta/{id}` - Buscar consulta por ID
- `POST /consulta` - Criar nova consulta
- `PUT /consulta` - Atualizar consulta

O Kong valida o JWT e extrai a ROLE do token, enviando apenas a role no header `X-User-Role` para o serviço de agendamento.

## Como Executar

### Pré-requisitos
- Docker
- Docker Compose

### Passos

1. Clone o repositório:
```bash
git clone <repository-url>
cd techchallenge3
```

2. Configure as variáveis de ambiente:
```bash
cd docker
# Verifique o arquivo .env
```

3. Inicie os serviços:
```bash
docker-compose up -d --build
```

4. Verifique os logs:
```bash
docker-compose logs -f
```

### Portas dos Serviços
- **Kong Proxy**: http://localhost:8080
- **Kong Admin**: http://localhost:8001
- **Auth Service**: http://localhost:8081
- **Agendamento Service**: http://localhost:8082
- **MariaDB**: localhost:3366

## Exemplos de Uso

### 1. Registrar um novo usuário
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Dr. João Silva",
    "documento": "12345678900",
    "email": "joao@example.com",
    "senha": "senha123",
    "tipoUsuario": "MEDICO"
  }'
```

### 2. Fazer login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@example.com",
    "senha": "senha123"
  }'
```

Resposta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 3. Criar uma consulta (com token)
```bash
curl -X POST http://localhost:8080/consulta \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <seu-token-jwt>" \
  -d '{
    "idMedico": 1,
    "idPaciente": 2,
    "descricao": "Consulta de rotina",
    "diaHoraConsulta": "2025-12-01T10:00:00",
    "motivoConsulta": "Check-up anual"
  }'
```

### 4. Buscar uma consulta (com token)
```bash
curl -X GET http://localhost:8080/consulta/1 \
  -H "Authorization: Bearer <seu-token-jwt>"
```

### 5. Atualizar uma consulta (com token)
```bash
curl -X PUT http://localhost:8080/consulta \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <seu-token-jwt>" \
  -d '{
    "id": 1,
    "idMedico": 1,
    "idPaciente": 2,
    "descricao": "Consulta de retorno",
    "diaHoraConsulta": "2025-12-15T14:00:00",
    "status": "ANDAMENTO",
    "motivoConsulta": "Retorno do check-up"
  }'
```

## Parar os Serviços
```bash
docker-compose down
```

## Remover volumes (limpar banco de dados)
```bash
docker-compose down -v
```

## Desenvolvimento Local

### Auth Service
```bash
cd app-service-auth
./mvnw spring-boot:run
```

### Agendamento Service
```bash
cd app-service-agendamento
./mvnw spring-boot:run
```

## Observações
- O token JWT expira em 2 horas
- O Kong remove o header `Authorization` antes de enviar para o serviço de agendamento
- O Kong adiciona os headers `X-User-Role` e `X-User-Id` com os dados do token
- As validações de permissão são feitas no serviço de agendamento baseado na role

