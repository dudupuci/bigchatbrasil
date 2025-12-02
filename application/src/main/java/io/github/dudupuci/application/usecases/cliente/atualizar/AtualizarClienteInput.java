package io.github.dudupuci.application.usecases.cliente.atualizar;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.enums.Sexo;

public record AtualizarClienteInput(
        Long id,
        String nome,
        String sexo,
        String email,
        String documento,
        String telefone,
        String sobre
) {

    public static Cliente criarEntidade(AtualizarClienteInput input) {
        final var cliente = new Cliente();
        cliente.setId(input.id());
        cliente.setNome(input.nome());
        cliente.setSexo(Sexo.valueOf(input.sexo()));
        cliente.setEmail(input.email());
        cliente.setCpfCnpj(input.documento());
        cliente.setTelefone(input.telefone());
        cliente.setSobre(input.sobre());
        return cliente;
    }

}

