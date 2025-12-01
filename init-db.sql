-- Script de inicialização do banco de dados
-- Cria o schema se necessário

CREATE SCHEMA IF NOT EXISTS public;
CREATE SCHEMA IF NOT EXISTS bcb;
CREATE SCHEMA IF NOT EXISTS clientes;

-- Comentários para documentação
COMMENT ON DATABASE bigchatbrasil IS 'Banco de dados do projeto BigChat Brasil';

