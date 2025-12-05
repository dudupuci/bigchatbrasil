-- Script de inicialização do banco de dados
-- Cria o schema se necessário

CREATE SCHEMA IF NOT EXISTS public;
CREATE SCHEMA IF NOT EXISTS bcb;
CREATE SCHEMA IF NOT EXISTS clientes;

-- Comentários para documentação
COMMENT ON DATABASE bigchatbrasil IS 'Banco de dados do projeto BigChat Brasil';

-- Índices para melhor performance
CREATE INDEX IF NOT EXISTS idx_mensagens_conversa_id ON mensagens(conversa_id);
CREATE INDEX IF NOT EXISTS idx_mensagens_remetente_id ON mensagens(remetente_id);
CREATE INDEX IF NOT EXISTS idx_mensagens_destinatario_id ON mensagens(destinatario_id);

