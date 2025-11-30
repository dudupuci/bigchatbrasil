# ⚡ EXECUÇÃO RÁPIDA - 3 Comandos

## Para Avaliadores

```bash
# 1. Validar (opcional mas recomendado)
validar-setup.bat

# 2. Subir tudo
docker-compose up -d

# 3. Verificar (aguardar 1-2 minutos)
curl http://localhost:8080/api/actuator/health
```

**Resultado esperado:** `{"status":"UP"}`

---

## Comandos Úteis

```bash
# Ver logs
docker-compose logs -f

# Ver status
docker-compose ps

# Parar tudo
docker-compose down

# Limpar tudo (APAGA DADOS!)
docker-compose down -v
```

---

## Testar API

```bash
# Criar cliente
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{"nome":"Teste","sexo":"MASCULINO","email":"teste@email.com","documento":"12345678900","telefone":"11999999999","sobre":"Teste"}'

# Buscar cliente
curl http://localhost:8080/api/clientes/1
```

---

## Acessar Banco

```bash
docker exec -it bigchatbrasil-postgres psql -U postgres -d bigchatbrasil
```

**Comandos SQL:**
```sql
\dt                    -- Ver tabelas
SELECT * FROM clientes;
SELECT * FROM empresas;
\q                     -- Sair
```

---

## Troubleshooting

**Erro de porta:**
```bash
docker-compose down
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

**Rebuild:**
```bash
docker-compose down -v
docker-compose build --no-cache
docker-compose up -d
```

**Ver logs de erro:**
```bash
docker-compose logs app | findstr ERROR
```

---

## ✅ Tudo OK quando:

- [ ] `docker-compose ps` mostra 2 containers UP
- [ ] Health check retorna `{"status":"UP"}`
- [ ] Consegue criar um cliente
- [ ] Consegue buscar o cliente criado
- [ ] Consegue conectar no banco

**Documentação completa:** `DOCKER-SETUP.md`

