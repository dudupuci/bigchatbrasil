@echo off
echo ====================================
echo  BigChat Brasil - Docker Setup
echo ====================================
echo.

if "%1"=="start" (
    echo Iniciando todos os serviços...
    docker-compose up -d
    echo.
    echo ✓ Serviços iniciados!
    echo ✓ API: http://localhost:8080/api
    echo ✓ PostgreSQL: localhost:5432
    goto end
)

if "%1"=="start-db" (
    echo Iniciando apenas o banco de dados...
    docker-compose up postgres -d
    echo.
    echo ✓ PostgreSQL iniciado!
    echo ✓ Host: localhost:5432
    echo ✓ Database: bigchatbrasil
    echo ✓ User: postgres
    echo ✓ Password: postgres
    goto end
)

if "%1"=="stop" (
    echo Parando todos os serviços...
    docker-compose down
    echo.
    echo ✓ Serviços parados!
    goto end
)

if "%1"=="restart" (
    echo Reiniciando todos os serviços...
    docker-compose restart
    echo.
    echo ✓ Serviços reiniciados!
    goto end
)

if "%1"=="logs" (
    echo Mostrando logs...
    docker-compose logs -f
    goto end
)

if "%1"=="clean" (
    echo Limpando containers e volumes...
    docker-compose down -v
    echo.
    echo ✓ Containers e volumes removidos!
    goto end
)

if "%1"=="build" (
    echo Reconstruindo imagens...
    docker-compose build --no-cache
    echo.
    echo ✓ Imagens reconstruídas!
    goto end
)

echo Uso: docker-manager.bat [comando]
echo.
echo Comandos disponíveis:
echo   start      - Inicia todos os serviços (banco + API)
echo   start-db   - Inicia apenas o banco de dados
echo   stop       - Para todos os serviços
echo   restart    - Reinicia todos os serviços
echo   logs       - Mostra os logs em tempo real
echo   clean      - Remove containers e volumes
echo   build      - Reconstrói as imagens Docker
echo.
echo Exemplos:
echo   docker-manager.bat start
echo   docker-manager.bat start-db
echo   docker-manager.bat logs

:end
# BigChat Brasil

## 🚀 Como rodar o projeto

### Pré-requisitos
- Docker
- Docker Compose

### Usando Docker Compose (Recomendado)

#### 1. Subir apenas o banco de dados
```bash
docker-compose up postgres -d
```

#### 2. Subir toda a aplicação (banco + API)
```bash
docker-compose up -d
```

#### 3. Ver logs
```bash
docker-compose logs -f
```

#### 4. Parar os containers
```bash
docker-compose down
```

#### 5. Parar e remover volumes (limpa o banco)
```bash
docker-compose down -v
```

### Desenvolvimento Local

#### 1. Subir apenas o PostgreSQL via Docker
```bash
docker-compose up postgres -d
```

#### 2. Rodar a aplicação via IDE ou Maven
```bash
cd infrastructure
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### Acessos

- **API**: http://localhost:8080/api
- **PostgreSQL**:
  - Host: localhost
  - Port: 5432
  - Database: bigchatbrasil
  - Username: postgres
  - Password: postgres

## 📁 Estrutura do Projeto

```
bigchatbrasil/
├── domain/              # Camada de domínio (entidades, repositories)
├── application/         # Camada de aplicação (casos de uso)
├── infrastructure/      # Camada de infraestrutura (JPA, controllers, configs)
├── docker-compose.yml   # Configuração Docker Compose
├── Dockerfile          # Dockerfile para build da aplicação
└── init-db.sql         # Script de inicialização do banco
```

## 🏗️ Arquitetura

O projeto segue os princípios de **Clean Architecture**:

- **Domain**: Entidades de negócio e interfaces de repositórios
- **Application**: Casos de uso (CRUD para Cliente e Empresa)
- **Infrastructure**: Implementações técnicas (JPA, REST API, configurações)

## 📦 Tecnologias

- Java 21
- Spring Boot 4.0.0
- PostgreSQL 16
- Docker & Docker Compose
- Maven
- Lombok
- Hibernate/JPA

## 🔧 Variáveis de Ambiente

Você pode customizar as seguintes variáveis no `docker-compose.yml`:

```yaml
DB_HOST=postgres
DB_PORT=5432
DB_NAME=bigchatbrasil
DB_USERNAME=postgres
DB_PASSWORD=postgres
SPRING_PROFILES_ACTIVE=docker
```

## 📝 Endpoints Disponíveis

### Clientes
- `POST /api/clientes` - Criar cliente
- `PUT /api/clientes/{id}` - Atualizar cliente
- `GET /api/clientes/{id}` - Buscar cliente
- `DELETE /api/clientes/{id}` - Deletar cliente

### Empresas
- `POST /api/empresas` - Criar empresa
- `PUT /api/empresas/{id}` - Atualizar empresa
- `GET /api/empresas/{id}` - Buscar empresa
- `DELETE /api/empresas/{id}` - Deletar empresa

