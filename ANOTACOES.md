- O que eu teria feito se eu tivesse mais tempo?

1) Criaria exceções personalizadas para melhorar o tratamento de erros, como:
   1.1) EmailJaCadastradoException
   1.2) UsuarioNaoEncontradoException
   
2) Criaria pelo menos um ControllerAdvice para centralizar o tratamento de exceções na aplicação e retornar JSONs padronizados.
3) Revisaria os DTOs de retorno das API's para garantir que não estão retornando informações sensíveis.
4) Adicionaria/faria uma revisão aos logs em pontos estratégicos do código para facilitar o monitoramento.
  4.1) Os logs que adicionei foram mais para debug rápido, estava sem tempo.
 
5) Implementaria testes unitários para garantir a qualidade do código.
6) Implementaria testes de integração para garantir que os componentes da aplicação funcionam bem juntos.
   6.1) Eu utilizaria RestAssured para testar as API's REST. (Já usei antes)

7) Ajustaria a árvore de herança das entidades para melhorar a clareza e a manutenção do código.
   Principalmente na forma como estão fazendo login/cadastro agora.
    7.1) Por exemplo, criaria uma classe abstrata UsuarioApp.
    
8) Adicionaria paginação nas consultas que retornam listas grandes de dados para melhorar a performance.
   8.1) Eu gosto de trabalhar também com conexões manuais via JDBC para otimizar consultas complexas.
   8.2) Eu gosto de criar minhas próprias queries SQL.
   
9) Implementaria caching para melhorar a performance em consultas frequentes.
10) Criaria uma página de perfil para o usuário atualizar suas informações pessoais.

11) Criaria uma forma de selecionar/alterar planos de assinatura dentro do sistema.
   11.1) Dentro dos planos, também criaria uma cobrança fictícia para simular o processo de pagamento.
   11.2) Criaria um TransacaoFacade para gerenciar as transações de forma mais organizada.
 
12) Buscaria aprofundar mais meus conhecimentos em Cloud e Docker para melhorar a implantação e escalabilidade da aplicação.
   12.1) Como hoje trabalho mais com legado, quase não vejo mais essas tecnologias no dia a dia.

13) Me aventuraria a implementar monitoramento e alertas para a aplicação em produção.

14) Revisaria a segurança da aplicação, incluindo autenticação e autorização.

15) Como criei uma classe abstrata Notificacao, implementaria mais tipos de notificações além do chat-online
   15.1) NotificacaoSMS
   15.2) NotificacaoEmail

16) Implmentaria nas conversas, a funcionalidade de anexar arquivos (imagens, documentos, etc).
17) Implementaria nas conversas, a funcionalidade de emojis.
18) Criaria uma funcionalidade de busca nas conversas para facilitar a localização de mensagens antigas.
19) Adicionaria uma funcionalidade de "visto por último" para os usuários nas conversas.
20) Implementaria uma funcionalidade de bloqueio de usuários para evitar mensagens indesejadas.
21) Implementaria conversas em grupo para permitir que múltiplos usuários conversem juntos.
22) Criaria uma funcionalidade de notificações push para alertar os usuários sobre novas mensagens.
23) Adicionaria uma funcionalidade de status (online, offline, ocupado) para os usuários.
   

24) Git
   24.1) Criaria branches específicas para cada funcionalidade ou correção de bug.
   24.2) Faria commits pequenos e frequentes com mensagens claras e descritivas.
   24.4) Manteria a branch principal sempre estável e pronta para produção.