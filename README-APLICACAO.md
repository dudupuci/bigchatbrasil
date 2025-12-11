# 🐳 BigChatBrasil - Guia Docker

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- ✅ **Docker** (versão 20.10 ou superior)
- ✅ **Docker Compose** (versão 2.0 ou superior)

### Verificar instalação:

```bash
docker --version
docker-compose --version
```

---

## 🚀 Início Rápido

### 1️⃣ Clone o repositório

```bash
git clone https://github.com/dudupuci/bigchatbrasil.git
cd bigchatbrasil
```

### 2️⃣ Construa e inicie os containers

```bash
docker-compose up -d --build
```

**O que isso faz:**
- 🐘 Cria e inicia o PostgreSQL 16
- ☕ Compila a aplicação Java 21 com Maven
- 🚀 Inicia a aplicação Spring Boot
- 🔗 Conecta tudo em uma rede Docker

### 3️⃣ Aguarde a aplicação iniciar

```bash
# Acompanhe os logs
docker-compose logs -f app

# Aguarde até ver:
# "Started InfrastructureApplication in X seconds"
```

### 4️⃣ Acesse a aplicação

- **API Base:** http://localhost:8080/api
- **Swagger UI:** http://localhost:8080/api/swagger-ui.html
- **Health Check:** http://localhost:8080/api/actuator/health

---

## 📊 Comandos Úteis

### Ver logs

```bash
# Todos os logs
docker-compose logs -f

# Apenas app
docker-compose logs -f app

# Apenas banco
docker-compose logs -f postgres

# Últimas 100 linhas
docker-compose logs --tail=100 -f
```

### Verificar status

```bash
# Status dos containers
docker-compose ps

# Verificar saúde
docker inspect bigchatbrasil-app | grep -A 10 "Health"
docker inspect bigchatbrasil-postgres | grep -A 10 "Health"
```

### Parar aplicação

```bash
# Parar containers (mantém dados)
docker-compose stop

# Parar e remover containers (mantém dados)
docker-compose down

# Parar, remover containers E volumes (APAGA TUDO!)
docker-compose down -v
```

### Reiniciar aplicação

```bash
# Reiniciar apenas app
docker-compose restart app

# Reiniciar tudo
docker-compose restart

# Reconstruir e reiniciar
docker-compose up -d --build
```

### Acessar shell dos containers

```bash
# Shell do app
docker exec -it bigchatbrasil-app sh

# Shell do postgres
docker exec -it bigchatbrasil-postgres psql -U postgres -d bigchatbrasil
```

---

## 🗄️ Banco de Dados

### Conectar via cliente SQL

**Configurações:**
- Host: `localhost`
- Porta: `5432`
- Database: `bigchatbrasil`
- Usuário: `postgres`
- Senha: `postgres`

### SQL direto

```bash
# Entrar no PostgreSQL
docker exec -it bigchatbrasil-postgres psql -U postgres -d bigchatbrasil

# Listar tabelas
\dt

# Ver estrutura de uma tabela
\d clientes

# Query
SELECT * FROM clientes LIMIT 5;

# Sair
\q
```

### Backup do banco

```bash
# Fazer backup
docker exec bigchatbrasil-postgres pg_dump -U postgres bigchatbrasil > backup.sql

# Restaurar backup
docker exec -i bigchatbrasil-postgres psql -U postgres bigchatbrasil < backup.sql
```

---

## 🔧 Configurações Avançadas

### Alterar porta da aplicação

Edite `docker-compose.yml`:

```yaml
services:
  app:
    ports:
      - "9090:8080"  # Nova porta externa:porta interna
```

Depois:
```bash
docker-compose up -d
```

### Alterar memória da JVM

Edite `docker-compose.yml`:

```yaml
services:
  app:
    environment:
      JAVA_OPTS: "-Xmx1024m -Xms512m"  # Mais memória
```

### Usar banco de dados externo

Edite `docker-compose.yml` e remova o serviço `postgres`, depois configure:

```yaml
services:
  app:
    environment:
      DB_HOST: seu-banco.rds.amazonaws.com
      DB_PORT: 5432
      DB_NAME: bigchatbrasil
      DB_USERNAME: admin
      DB_PASSWORD: senhaSegura
```

---

## 🐛 Troubleshooting

### Problema: Porta 8080 já está em uso

**Solução 1:** Pare o que está usando a porta:
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

**Solução 2:** Mude a porta no `docker-compose.yml`

### Problema: Porta 5432 já está em uso

**Solução:** Pare o PostgreSQL local:
```bash
# Windows
net stop postgresql-x64-16

# Linux
sudo systemctl stop postgresql

# Mac
brew services stop postgresql
```

Ou mude a porta no `docker-compose.yml`:
```yaml
services:
  postgres:
    ports:
      - "5433:5432"  # Porta externa diferente
```

### Problema: App não inicia

```bash
# Ver logs detalhados
docker-compose logs app

# Verificar se o banco está saudável
docker-compose ps

# Restart forçado
docker-compose down
docker-compose up -d --build
```

### Problema: Erro de conexão com banco

```bash
# Verificar se postgres está saudável
docker inspect bigchatbrasil-postgres | grep Health

# Testar conexão manualmente
docker exec bigchatbrasil-postgres psql -U postgres -c "SELECT 1"

# Reiniciar apenas postgres
docker-compose restart postgres
```

### Problema: Build falha

```bash
# Limpar cache do Docker
docker builder prune -a

# Limpar tudo e rebuild
docker-compose down -v
docker system prune -a
docker-compose up -d --build
```

### Problema: Aplicação lenta

```bash
# Verificar uso de recursos
docker stats

# Ver logs de memória
docker-compose logs app | grep -i memory

# Aumentar memória da JVM (docker-compose.yml)
JAVA_OPTS: "-Xmx1024m -Xms512m"
```

---

## 🧪 Testando a Aplicação

### 1. Registrar um cliente

```bash
curl -X POST http://localhost:8080/api/registrar/cliente \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@test.com",
    "senha": "senha123",
    "confirmacaoSenha": "senha123",
    "cpfCnpj": "12345678901",
    "sexo": "MASCULINO",
    "telefone": "(11) 98765-4321"
  }'
```

### 2. Fazer login

```bash
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@test.com",
    "senha": "senha123",
    "tipo": "CLIENTE"
  }'
```

### 3. Testar health check

```bash
curl http://localhost:8080/api/actuator/health
```

---

## 📦 Estrutura do Projeto Docker

```
bigchatbrasil/
├── Dockerfile                 # Build da aplicação Java
├── docker-compose.yml         # Orquestração dos serviços
├── .dockerignore             # Arquivos ignorados no build
├── infrastructure/
│   └── src/
│       └── main/
│           └── resources/
│               └── application-docker.yml  # Config para Docker
└── README.Docker.md          # Este arquivo
```

---

## 🌐 Ambientes

### Desenvolvimento Local (sem Docker)

```bash
mvn spring-boot:run -pl infrastructure
```

### Desenvolvimento com Docker

```bash
docker-compose up -d
```

### Produção (exemplo AWS)

```yaml
# docker-compose.prod.yml
services:
  app:
    image: bigchatbrasil:latest
    environment:
      SPRING_PROFILES_ACTIVE: prod
      DB_HOST: rds.amazonaws.com
      # ... outras configs
```

---

## 📝 Notas Importantes

1. **Hibernate gerencia o schema:** Não precisa executar scripts SQL manualmente
2. **Dados persistem:** Os dados do PostgreSQL ficam salvos no volume `postgres_data`
3. **Hot reload:** Para ver mudanças no código, rebuild: `docker-compose up -d --build`
4. **Logs:** Use `docker-compose logs -f` para debug
5. **Health checks:** App leva ~60s para ficar "healthy" após iniciar

---

## 🎯 Checklist de Deploy

- [ ] Docker e Docker Compose instalados
- [ ] Portas 8080 e 5432 disponíveis
- [ ] Executou `docker-compose up -d --build`
- [ ] Aguardou app ficar "healthy" (logs)
- [ ] Testou http://localhost:8080/api/actuator/health
- [ ] Testou http://localhost:8080/api/swagger-ui.html
- [ ] Registrou um usuário de teste
- [ ] Fez login com sucesso

---

## 🆘 Suporte

Se encontrar problemas:

1. ✅ Consulte a seção **Troubleshooting** acima
2. ✅ Verifique os logs: `docker-compose logs -f`
3. ✅ Verifique o health: `docker-compose ps`
4. ✅ Reconstrua: `docker-compose down && docker-compose up -d --build`

---

## 🚀 Pronto para produção?

Para deploy em produção, considere:

- 🔐 Mudar senhas padrão do banco
- 🔐 Usar secrets/vault para credenciais
- 📊 Configurar monitoring (Prometheus/Grafana)
- 📝 Centralizar logs (ELK Stack)
- 🔄 Usar orquestradores (Kubernetes/ECS)
- 🌐 Configurar load balancer
- 💾 Backup automatizado do banco
- 🔒 SSL/TLS para HTTPS

---

**Última atualização:** 2025-12-11
**Versão Docker:** 20.10+
**Versão Java:** 21
**Versão Spring Boot:** 4.0.0

