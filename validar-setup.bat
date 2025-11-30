@echo off
echo ========================================
echo  Validacao Rapida - BigChat Brasil
echo ========================================
echo.

echo [1/5] Verificando arquivos necessarios...
if not exist "pom.xml" (
    echo ✗ ERRO: pom.xml nao encontrado
    goto error
)
if not exist "Dockerfile" (
    echo ✗ ERRO: Dockerfile nao encontrado
    goto error
)
if not exist "docker-compose.yml" (
    echo ✗ ERRO: docker-compose.yml nao encontrado
    goto error
)
echo ✓ Todos os arquivos necessarios existem
echo.

echo [2/5] Verificando Docker...
docker --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ✗ ERRO: Docker nao esta instalado ou nao esta rodando
    goto error
)
echo ✓ Docker instalado
echo.

echo [3/5] Verificando docker-compose...
docker-compose --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ✗ ERRO: docker-compose nao esta instalado
    goto error
)
echo ✓ docker-compose instalado
echo.

echo [4/5] Verificando portas...
netstat -an | findstr ":5432 " >nul
if %ERRORLEVEL% EQU 0 (
    echo ! AVISO: Porta 5432 esta em uso
    echo   Para limpar: docker-compose down
)
netstat -an | findstr ":8080 " >nul
if %ERRORLEVEL% EQU 0 (
    echo ! AVISO: Porta 8080 esta em uso
    echo   Para limpar: docker-compose down
)
echo ✓ Verificacao de portas concluida
echo.

echo [5/5] Verificando estrutura do projeto...
if not exist "domain\pom.xml" (
    echo ✗ ERRO: domain/pom.xml nao encontrado
    goto error
)
if not exist "application\pom.xml" (
    echo ✗ ERRO: application/pom.xml nao encontrado
    goto error
)
if not exist "infrastructure\pom.xml" (
    echo ✗ ERRO: infrastructure/pom.xml nao encontrado
    goto error
)
echo ✓ Estrutura do projeto OK
echo.

echo ========================================
echo  ✓ VALIDACAO COMPLETA!
echo ========================================
echo.
echo Proximo passo:
echo   docker-compose up -d
echo.
goto end

:error
echo.
echo ========================================
echo  ✗ VALIDACAO FALHOU!
echo ========================================
echo.
echo Verifique os erros acima e corrija-os antes de continuar.
exit /b 1

:end

