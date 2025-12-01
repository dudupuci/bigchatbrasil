@echo off
echo ========================================
echo  Rodar App Local via Maven
echo ========================================
echo.

cd infrastructure

echo Iniciando aplicacao com profile LOCAL...
echo.
echo Configuracao:
echo - Profile: local
echo - PostgreSQL: localhost:5432
echo - Database: bigchatbrasil
echo - DDL: create-drop (recria tabelas)
echo - Port: 8080
echo.
echo Pressione Ctrl+C para parar
echo.
echo ----------------------------------------

mvn spring-boot:run -Dspring-boot.run.profiles=local

