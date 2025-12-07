package io.github.dudupuci.application.usecases.cliente.criar;

import io.github.dudupuci.domain.enums.TipoDocumento;
import io.github.dudupuci.domain.repositories.ClienteRepository;

import java.util.Base64;

public class CriarClienteUseCaseImpl extends CriarClienteUseCase {

    private final ClienteRepository clienteRepository;

    public CriarClienteUseCaseImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public CriarClienteOutput execute(CriarClienteInput criarClienteInput) {
        try {
            final var cliente = CriarClienteInput.criarEntidade(criarClienteInput);

            cliente.validar();
            cliente.setSenha(this.encodePassword(cliente.getSenha()));

            final var clienteCriado = this.clienteRepository.salvar(cliente);

            return new CriarClienteOutput(
                    clienteCriado.getId(),
                    clienteCriado.getNome(),
                    true
            );

        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }

    /**
     * Codifica senha com Base64 simples
     */
    private String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
