# 🚀 GUIA RÁPIDO - BigChat Brasil

## ⚡ Quick Start (3 passos)

```bash
1. docker-compose up -d
2. Aguarde 1-2 minutos
3. Acesse: http://localhost:8080/api/actuator/health
```

## 📋 Checklist de Verificação

- [ ] Docker instalado e rodando
- [ ] Portas 5432 e 8080 livres
- [ ] `docker-compose ps` mostra containers UP
- [ ] Health check retorna `{"status":"UP"}`

## 🗄️ Banco de Dados

**Host:** localhost | **Port:** 5432 | **DB:** bigchatbrasil  
**User:** postgres | **Pass:** postgres

```bash
# Conectar
docker exec -it bigchatbrasil-postgres psql -U postgres -d bigchatbrasil

# Ver tabelas
\dt

# Sair
\q
```

## 🌐 Endpoints

**Base URL:** `http://localhost:8080/api`

### Clientes
```bash
POST   /clientes      # Criar
GET    /clientes/{id} # Buscar
PUT    /clientes/{id} # Atualizar
DELETE /clientes/{id} # Deletar
```

### Empresas
```bash
POST   /empresas      # Criar
GET    /empresas/{id} # Buscar
PUT    /empresas/{id} # Atualizar
DELETE /empresas/{id} # Deletar
```

## 🧪 Teste Rápido

### Criar Cliente
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

### Buscar Cliente
```bash
curl http://localhost:8080/api/clientes/1
```

### Criar Empresa
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

## 🐳 Comandos Docker Essenciais

```bash
# Iniciar
docker-compose up -d

# Ver status
docker-compose ps

# Ver logs
docker-compose logs -f

# Parar
docker-compose down

# Limpar tudo (APAGA DADOS!)
docker-compose down -v

# Rebuild
docker-compose up -d --build
```

## 🛠️ Scripts Helper (Windows)

```cmd
docker-manager.bat start      # Inicia
docker-manager.bat logs       # Logs
docker-manager.bat stop       # Para
test-api.bat                  # Testa API
```

## 🐛 Troubleshooting

### API não responde
```bash
docker-compose logs app
curl http://localhost:8080/api/actuator/health
```

### Banco não conecta
```bash
docker exec bigchatbrasil-postgres pg_isready -U postgres
docker-compose logs postgres
```

### Porta em uso
```cmd
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Reset completo
```bash
docker-compose down -v
docker system prune -af
docker-compose up -d --build
```

## 📁 Estrutura do Projeto

```
bigchatbrasil/
├── domain/              # Entidades, Repositórios
├── application/         # Casos de Uso
├── infrastructure/      # JPA, REST, Configs
├── docker-compose.yml   # Orquestração
├── Dockerfile          # Build da app
└── README.md           # Documentação
```

## 🏗️ Arquitetura

**Clean Architecture:**
- Domain (núcleo do negócio)
- Application (casos de uso)
- Infrastructure (frameworks e drivers)

## 📚 Documentação Completa

- `README.md` - Visão geral
- `DOCKER-SETUP.md` - Setup detalhado
- `COMANDOS-UTEIS.md` - Referência de comandos

## 🎯 Tecnologias

- **Java 21** + Spring Boot 4.0
- **PostgreSQL 16** (Alpine)
- **Docker** + Docker Compose
- **Maven** 3.9+
- **Hibernate/JPA**
- **Lombok**

## ✅ Validação Final

```bash
# 1. Containers rodando?
docker-compose ps

# 2. API responde?
curl http://localhost:8080/api/actuator/health

# 3. Banco acessível?
docker exec bigchatbrasil-postgres pg_isready -U postgres

# 4. Teste completo
test-api.bat  # ou ./test-api.sh
```

---

**Tudo pronto! 🚀** Para mais detalhes, veja `DOCKER-SETUP.md`

