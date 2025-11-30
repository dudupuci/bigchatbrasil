# 📋 Comandos Úteis - BigChat Brasil

## 🚀 Início Rápido

```bash
# Clonar e iniciar
git clone <repo-url>
cd bigchatbrasil
docker-compose up -d

# Verificar status
docker-compose ps

# Ver logs
docker-compose logs -f
```

## 🐳 Docker Compose

### Iniciar Serviços
```bash
# Tudo (banco + API)
docker-compose up -d

# Apenas PostgreSQL
docker-compose up postgres -d

# Com rebuild
docker-compose up -d --build

# Sem modo daemon (ver logs direto)
docker-compose up
```

### Parar Serviços
```bash
# Parar containers
docker-compose down

# Parar e remover volumes (APAGA DADOS!)
docker-compose down -v

# Parar apenas um serviço
docker-compose stop app
docker-compose stop postgres
```

### Logs
```bash
# Todos os logs (tempo real)
docker-compose logs -f

# Logs da aplicação
docker-compose logs -f app

# Logs do banco
docker-compose logs -f postgres

# Últimas 100 linhas
docker-compose logs --tail=100

# Últimas 50 linhas de cada serviço
docker-compose logs --tail=50 -f
```

### Status e Informações
```bash
# Ver containers rodando
docker-compose ps

# Ver detalhes de um serviço
docker-compose exec app env

# Ver uso de recursos
docker stats bigchatbrasil-app bigchatbrasil-postgres
```

## 🗄️ PostgreSQL

### Conectar no Banco
```bash
# Via Docker exec
docker exec -it bigchatbrasil-postgres psql -U postgres -d bigchatbrasil

# Via psql local (se tiver instalado)
psql -h localhost -p 5432 -U postgres -d bigchatbrasil
```

### Comandos SQL Úteis
```sql
-- Ver tabelas
\dt

-- Descrever tabela
\d clientes
\d empresas

-- Ver dados
SELECT * FROM clientes;
SELECT * FROM empresas;

-- Contar registros
SELECT COUNT(*) FROM clientes;
SELECT COUNT(*) FROM empresas;

-- Limpar tabelas
TRUNCATE clientes RESTART IDENTITY CASCADE;
TRUNCATE empresas RESTART IDENTITY CASCADE;

-- Sair
\q
```

### Executar SQL via Docker
```bash
# Executar comando SQL direto
docker exec bigchatbrasil-postgres psql -U postgres -d bigchatbrasil -c "SELECT * FROM clientes;"

# Executar arquivo SQL
docker exec -i bigchatbrasil-postgres psql -U postgres -d bigchatbrasil < script.sql

# Fazer backup
docker exec bigchatbrasil-postgres pg_dump -U postgres bigchatbrasil > backup.sql

# Restaurar backup
docker exec -i bigchatbrasil-postgres psql -U postgres -d bigchatbrasil < backup.sql
```

## 🔍 Testes da API

### Health Check
```bash
# Verificar se API está UP
curl http://localhost:8080/api/actuator/health

# Com formato bonito (se tiver jq instalado)
curl -s http://localhost:8080/api/actuator/health | jq '.'
```

### CRUD de Clientes

#### Criar Cliente
```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "sexo": "MASCULINO",
    "email": "joao@email.com",
    "documento": "12345678900",
    "telefone": "11999999999",
    "sobre": "Cliente teste"
  }'
```

#### Buscar Cliente
```bash
# Por ID
curl http://localhost:8080/api/clientes/1

# Com formato bonito
curl -s http://localhost:8080/api/clientes/1 | jq '.'
```

#### Atualizar Cliente
```bash
curl -X PUT http://localhost:8080/api/clientes/1 \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "nome": "João Silva Atualizado",
    "sexo": "MASCULINO",
    "email": "joao.novo@email.com",
    "documento": "12345678900",
    "telefone": "11988888888",
    "sobre": "Cliente atualizado"
  }'
```

#### Deletar Cliente
```bash
curl -X DELETE http://localhost:8080/api/clientes/1
```

### CRUD de Empresas

#### Criar Empresa
```bash
curl -X POST http://localhost:8080/api/empresas \
  -H "Content-Type: application/json" \
  -d '{
    "razaoSocial": "Empresa Teste Ltda",
    "cnpj": "12345678000190",
    "telefone": "1133334444",
    "email": "contato@empresa.com"
  }'
```

#### Buscar Empresa
```bash
curl http://localhost:8080/api/empresas/1
```

#### Atualizar Empresa
```bash
curl -X PUT http://localhost:8080/api/empresas/1 \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "razaoSocial": "Empresa Teste Ltda - Atualizada",
    "cnpj": "12345678000190",
    "telefone": "1144445555",
    "email": "novo@empresa.com"
  }'
```

#### Deletar Empresa
```bash
curl -X DELETE http://localhost:8080/api/empresas/1
```

## 🐛 Debug e Troubleshooting

### Ver Logs Detalhados
```bash
# Logs de erro da aplicação
docker-compose logs app | grep -i error

# Logs de erro do banco
docker-compose logs postgres | grep -i error

# Logs com timestamp
docker-compose logs -t -f
```

### Inspecionar Containers
```bash
# Ver configuração do container
docker inspect bigchatbrasil-app

# Ver variáveis de ambiente
docker exec bigchatbrasil-app env

# Ver processos rodando
docker exec bigchatbrasil-app ps aux

# Entrar no container (shell)
docker exec -it bigchatbrasil-app sh
```

### Network e Conectividade
```bash
# Ver networks
docker network ls

# Inspecionar network
docker network inspect bigchatbrasil_bigchatbrasil-network

# Testar conexão entre containers
docker exec bigchatbrasil-app ping postgres
docker exec bigchatbrasil-app nc -zv postgres 5432
```

### Performance
```bash
# Ver uso de recursos em tempo real
docker stats

# Ver uso de espaço
docker system df

# Ver logs de build
docker-compose build --progress=plain
```

## 🔄 Rebuild e Reset

### Rebuild da Aplicação
```bash
# Rebuild sem cache
docker-compose build --no-cache

# Rebuild e reiniciar
docker-compose up -d --build

# Rebuild apenas a app
docker-compose build --no-cache app
```

### Reset Completo
```bash
# Parar tudo e limpar
docker-compose down -v

# Remover imagens órfãs
docker image prune -f

# Remover volumes órfãos
docker volume prune -f

# Rebuild do zero
docker-compose build --no-cache
docker-compose up -d
```

## 📦 Maven (Desenvolvimento Local)

### Build do Projeto
```bash
# Build completo
mvn clean install

# Build sem testes
mvn clean install -DskipTests

# Apenas compilar
mvn compile

# Rodar testes
mvn test
```

### Rodar Aplicação Localmente
```bash
# Com profile local
cd infrastructure
mvn spring-boot:run -Dspring-boot.run.profiles=local

# Com profile docker
mvn spring-boot:run -Dspring-boot.run.profiles=docker

# Com debug habilitado
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

## 🧪 Scripts Helper

### Windows
```cmd
# Gerenciar Docker
docker-manager.bat start      # Inicia tudo
docker-manager.bat start-db   # Apenas banco
docker-manager.bat stop       # Para tudo
docker-manager.bat logs       # Ver logs
docker-manager.bat clean      # Limpar volumes
docker-manager.bat build      # Rebuild

# Testar API
test-api.bat
```

### Linux/Mac
```bash
# Dar permissão de execução
chmod +x test-api.sh

# Executar testes
./test-api.sh
```

## 📊 Monitoramento

### Verificar Health
```bash
# Status da API
curl -s http://localhost:8080/api/actuator/health | jq '.'

# Status do banco
docker exec bigchatbrasil-postgres pg_isready -U postgres
```

### Ver Métricas (se Actuator estiver configurado)
```bash
# Informações gerais
curl http://localhost:8080/api/actuator/info

# Métricas (se habilitado)
curl http://localhost:8080/api/actuator/metrics
```

## 🔐 Segurança

### Alterar Credenciais do Banco
Edite o `docker-compose.yml`:
```yaml
environment:
  POSTGRES_USER: seu_usuario
  POSTGRES_PASSWORD: sua_senha_forte
  POSTGRES_DB: bigchatbrasil
```

E também as variáveis no serviço `app`:
```yaml
environment:
  DB_USERNAME: seu_usuario
  DB_PASSWORD: sua_senha_forte
```

## 📝 Notas Importantes

- **Porta 5432**: PostgreSQL
- **Porta 8080**: API Spring Boot
- **Context Path**: `/api`
- **Profile Padrão**: `docker`
- **DDL Mode**: `update` (não apaga dados)

## 🆘 Comandos de Emergência

```bash
# Matar tudo e recomeçar
docker-compose down -v
docker system prune -af
docker volume prune -f
docker-compose up -d --build

# Ver o que está usando as portas (Windows)
netstat -ano | findstr :5432
netstat -ano | findstr :8080

# Ver o que está usando as portas (Linux/Mac)
lsof -i :5432
lsof -i :8080
```

