package io.github.dudupuci.application.usecases.cliente.criar;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.enums.Sexo;

public record CriarClienteInput(
        String nome,
        String sexo,
        String email,
        String documento,
        String telefone,
        String sobre
) {

    public static Cliente criarEntidade(CriarClienteInput input) {
        final var cliente = new Cliente();
        cliente.setNome(input.nome());
        cliente.setSexo(Sexo.valueOf(input.sexo()));
        cliente.setEmail(input.email());
        cliente.setDocumento(input.documento());
        cliente.setTelefone(input.telefone());
        cliente.setSobre(input.sobre());
        return cliente;
    }

}
