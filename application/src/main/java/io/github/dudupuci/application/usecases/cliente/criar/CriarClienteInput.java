package io.github.dudupuci.application.usecases.cliente.criar;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.enums.Assinatura;
import io.github.dudupuci.domain.enums.Sexo;
import io.github.dudupuci.domain.enums.TipoDocumento;

public record CriarClienteInput(
        String nome,
        String sobrenome,
        String sexo,
        String email,
        String cpfCnpj,
        String senha,
        String confirmacaoSenha,
        String telefone,
        String sobre
) {

    public static Cliente criarEntidade(CriarClienteInput input) {
        final var cliente = new Cliente();
        cliente.setNome(input.nome());
        cliente.setSobrenome(input.sobre);
        cliente.setSexo(Sexo.fromDescricao(input.sexo));
        cliente.setEmail(input.email());
        cliente.setCpfCnpj(input.cpfCnpj());
        cliente.setTelefone(input.telefone());
        cliente.setSobre(input.sobre());
        cliente.setSenha(input.senha());
        cliente.setConfirmacaoSenha(input.confirmacaoSenha());

        cliente.setTipoDocumento(getTipoDocumento(cliente.getCpfCnpj()));
        cliente.setPlano(Assinatura.GRATIS);

        return cliente;
    }

    private static TipoDocumento getTipoDocumento(String documento) {
        if (documento.length() == 11) {
            return TipoDocumento.CPF;
        } else if (documento.length() == 14) {
            return TipoDocumento.CNPJ;
        } else {
            throw new IllegalArgumentException("Documento inválido");
        }
    }

}
