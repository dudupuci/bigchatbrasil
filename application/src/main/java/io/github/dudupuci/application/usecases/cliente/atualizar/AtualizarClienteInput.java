package io.github.dudupuci.application.usecases.cliente.atualizar;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.enums.Sexo;
import io.github.dudupuci.domain.enums.TipoDocumento;

import java.math.BigDecimal;

/**
 * Input para atualização parcial de cliente
 * TODOS os campos são opcionais (exceto ID)
 */
public record AtualizarClienteInput(
        Long id,
        String nome,
        String sobrenome,
        String sexo,
        String email,
        String cpfCnpj,
        String tipoDocumento,
        String plano,
        BigDecimal saldo,
        BigDecimal limite,
        String telefone,
        String sobre,
        Boolean isAtivo,
        String senha,
        String confirmacaoSenha
) {
    /**
     * Converte o input em uma entidade Cliente do domínio
     * Campos nulos são mantidos como null para atualização parcial
     */
    public Cliente paraEntidade() {
        Cliente cliente = new Cliente();
        cliente.setId(this.id);
        cliente.setNome(this.nome);
        cliente.setSobrenome(this.sobrenome);
        cliente.setSexo(this.sexo != null ? Sexo.fromDescricao(this.sexo) : null);
        cliente.setEmail(this.email);
        cliente.setCpfCnpj(this.cpfCnpj);
        cliente.setTipoDocumento(this.tipoDocumento != null ? TipoDocumento.fromDescricao(this.tipoDocumento) : null);
        cliente.setTelefone(this.telefone);
        cliente.setSobre(this.sobre);
        return cliente;
    }
}
