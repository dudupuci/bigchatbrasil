package io.github.dudupuci.application.usecases.cliente.atualizar;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.enums.Sexo;
import io.github.dudupuci.domain.enums.TipoDocumento;

public record AtualizarClienteInput(
        Long id,
        String nome,
        String sobrenome,
        String sexo,
        String email,
        String cpfCnpj,
        String tipoDocumento,
        String telefone,
        String sobre
) {

    public static Cliente atualizarEntidade(AtualizarClienteInput input) {
        final var cliente = new Cliente();
        cliente.setId(input.id());
        cliente.setNome(input.nome());
        cliente.setSobrenome(input.sobrenome());
        cliente.setSexo(Sexo.fromDescricao(input.sexo()));
        cliente.setEmail(input.email());
        cliente.setCpfCnpj(input.cpfCnpj());
        cliente.setTipoDocumento(TipoDocumento.fromDescricao(input.tipoDocumento()));
        cliente.setTelefone(input.telefone());
        cliente.setSobre(input.sobre());
        return cliente;
    }

}

