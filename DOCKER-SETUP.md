# 🐳 Setup Docker - BigChat Brasil

## Para os Avaliadores

Este guia foi criado para facilitar a execução do projeto em um ambiente limpo usando Docker.

## ⚡ Quick Start

### Opção 1: Rodar TUDO com Docker (Mais Simples)

```bash
# Na raiz do projeto
docker-compose up -d
```

Aguarde alguns minutos para o build e inicialização. A API estará disponível em:
- http://localhost:8080/api
- Health check: http://localhost:8080/api/actuator/health

### Opção 2: Apenas o Banco de Dados no Docker

```bash
# Subir apenas o PostgreSQL
docker-compose up postgres -d

# Rodar a aplicação via Maven
cd infrastructure
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## 📋 Pré-requisitos

- Docker Desktop instalado e rodando
- Docker Compose (já vem com Docker Desktop)
- Git (para clonar o repositório)

## 🔧 Configuração Detalhada

### 1. Clonar o Repositório

```bash
git clone <url-do-repositorio>
cd bigchatbrasil
```

### 2. Verificar Docker

```bash
docker --version
docker-compose --version
```

### 3. Iniciar os Serviços

#### Windows
```cmd
docker-manager.bat start
```

#### Linux/Mac ou Windows (manual)
```bash
docker-compose up -d
```

### 4. Verificar Status

```bash
# Ver containers rodando
docker-compose ps

# Ver logs
docker-compose logs -f

# Ver logs apenas da aplicação
docker-compose logs -f app

# Ver logs apenas do banco
docker-compose logs -f postgres
```

### 5. Testar a API

```bash
# Health check
curl http://localhost:8080/api/actuator/health

# Criar um cliente (exemplo)
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

## 🗄️ Acesso ao Banco de Dados

Você pode conectar ao PostgreSQL usando qualquer cliente SQL:

- **Host**: localhost
- **Port**: 5432
- **Database**: bigchatbrasil
- **Username**: postgres
- **Password**: postgres

### Exemplo com psql (linha de comando)

```bash
docker exec -it bigchatbrasil-postgres psql -U postgres -d bigchatbrasil
```

### Exemplo com DBeaver, DataGrip, pgAdmin

Crie uma nova conexão com as credenciais acima.

## 🛑 Parar os Serviços

```bash
# Parar sem remover volumes (mantém dados)
docker-compose down

# Parar e remover volumes (limpa tudo)
docker-compose down -v
```

## 🔄 Rebuild da Aplicação

Se você fez alterações no código:

```bash
# Rebuild e restart
docker-compose up -d --build
```

## 🐛 Troubleshooting

### Porta já em uso

Se a porta 5432 ou 8080 já estiver em uso:

**Opção 1**: Parar o serviço que está usando a porta
**Opção 2**: Modificar as portas no `docker-compose.yml`:

```yaml
ports:
  - "5433:5432"  # PostgreSQL na porta 5433
  - "8081:8080"  # API na porta 8081
```

### Container não inicia

```bash
# Ver logs detalhados
docker-compose logs

# Remover tudo e começar do zero
docker-compose down -v
docker-compose up -d
```

### Erro de conexão com banco

Aguarde alguns segundos. O banco precisa estar completamente iniciado antes da aplicação conectar.

```bash
# Verificar health do banco
docker-compose exec postgres pg_isready -U postgres
```

## 📁 Estrutura de Arquivos Docker

```
bigchatbrasil/
├── docker-compose.yml       # Orquestração dos containers
├── Dockerfile              # Build da aplicação Java
├── init-db.sql            # Script de inicialização do DB
├── docker-manager.bat     # Helper script para Windows
└── .dockerignore          # Arquivos ignorados no build
```

## 🏗️ Como Funciona

1. **PostgreSQL Container**: 
   - Inicia primeiro
   - Cria o banco `bigchatbrasil`
   - Executa o script `init-db.sql`
   - Expõe porta 5432

2. **Aplicação Container**:
   - Aguarda o PostgreSQL estar healthy
   - Faz build da aplicação (multi-stage build)
   - Inicia Spring Boot
   - Expõe porta 8080

3. **Network**:
   - Containers se comunicam via rede interna
   - Aplicação acessa o banco via hostname `postgres`

## ✅ Checklist de Verificação

- [ ] Docker Desktop está rodando
- [ ] Portas 5432 e 8080 estão livres
- [ ] `docker-compose up -d` executou sem erros
- [ ] `docker-compose ps` mostra containers rodando
- [ ] http://localhost:8080/api/actuator/health retorna `{"status":"UP"}`
- [ ] É possível criar um cliente via API

## 📞 Suporte

Se encontrar problemas, verifique:

1. Logs: `docker-compose logs -f`
2. Status dos containers: `docker-compose ps`
3. Health do banco: `docker-compose exec postgres pg_isready -U postgres`
4. Versão do Docker: `docker --version`

## 🎯 Ambientes

- **local**: Desenvolvimento local (IDE + Docker DB)
- **docker**: Ambiente completo em Docker
- **Produção**: Configurar variáveis de ambiente conforme necessário

