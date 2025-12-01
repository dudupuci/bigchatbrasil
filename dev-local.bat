@echo off
echo ========================================
echo  Modo Desenvolvimento Local
echo ========================================
echo.

echo Este script ira:
echo 1. Parar o container da aplicacao (se existir)
echo 2. Manter apenas o PostgreSQL rodando
echo 3. Preparar ambiente para rodar app pela IDE
echo.

set /p confirm="Deseja continuar? (S/N): "
if /i not "%confirm%"=="S" (
    echo Operacao cancelada.
    exit /b 0
)

echo.
echo [1/3] Parando container da aplicacao...
docker-compose stop app 2>nul
docker-compose rm -f app 2>nul
echo ✓ Container da aplicacao parado

echo.
echo [2/3] Verificando PostgreSQL...
docker ps | findstr bigchatbrasil-postgres >nul
if %ERRORLEVEL% NEQ 0 (
    echo ! PostgreSQL nao esta rodando. Subindo...
    docker-compose up postgres -d
    echo ✓ PostgreSQL iniciado
    echo Aguardando banco ficar pronto...
    timeout /t 5 /nobreak >nul
) else (
    echo ✓ PostgreSQL ja esta rodando
)

echo.
echo [3/3] Verificando conectividade...
docker exec bigchatbrasil-postgres pg_isready -U postgres >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo ✓ PostgreSQL esta pronto
) else (
    echo ! Aguardando PostgreSQL...
    timeout /t 3 /nobreak >nul
)

echo.
echo ========================================
echo  ✓ Ambiente pronto!
echo ========================================
echo.
echo PostgreSQL: localhost:5432
echo Database:   bigchatbrasil
echo User:       postgres
echo Pass:       postgres
echo.
echo Proximo passo:
echo   Rodar aplicacao pela IDE com profile "local"
echo.
echo Via Maven:
echo   cd infrastructure
echo   mvn spring-boot:run -Dspring-boot.run.profiles=local
echo.
echo Via IntelliJ:
echo   1. Abrir Main.java
echo   2. Run/Debug
echo   3. VM Options: -Dspring.profiles.active=local
echo.
echo Para voltar ao modo Docker:
echo   docker-compose up -d
echo.

