package io.github.dudupuci.infrastructure.persistence.facade.login;

import io.github.dudupuci.application.usecases.cliente.buscar.BuscarClienteOutput;
import io.github.dudupuci.application.usecases.empresa.buscar.BuscarEmpresaOutput;
import io.github.dudupuci.domain.enums.TipoUsuario;
import io.github.dudupuci.domain.validators.BcbEntityValidator;
import io.github.dudupuci.infrastructure.persistence.facade.clientes.ClienteFacade;
import io.github.dudupuci.infrastructure.persistence.facade.empresas.EmpresaFacade;
import io.github.dudupuci.infrastructure.security.SimpleSessionManager;
import io.github.dudupuci.infrastructure.web.dtos.request.login.LoginApiRequest;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class LoginFacadeImpl implements LoginFacade {

    private final ClienteFacade clienteFacade;
    private final EmpresaFacade empresaFacade;
    private final SimpleSessionManager sessionManager;

    public LoginFacadeImpl(
            ClienteFacade clienteFacade,
            EmpresaFacade empresaFacade,
            SimpleSessionManager sessionManager
    ) {
        this.clienteFacade = clienteFacade;
        this.empresaFacade = empresaFacade;
        this.sessionManager = sessionManager;
    }

    /**
     * Valida senha comparando com a codificada
     */
    private boolean matchPassword(String rawPassword, String encodedPassword) {
        return encodePassword(rawPassword).equals(encodedPassword);
    }

    /**
     * Codifica senha com Base64 simples
     */
    private String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    /**
     * Realiza login de CLIENTE ou EMPRESA
     * <p>Guarda sessão com tipo de usuário</p>
     * @param request
     * @return
     */
    @Override
    public String doLogin(LoginApiRequest request) {
        String sessionId = null;

        if (TipoUsuario.CLIENTE.equals(request.tipo())) {
            BuscarClienteOutput cliente = clienteFacade.buscarPorEmail(request.email());

            if (BcbEntityValidator.isEntityNotNull(cliente.toDomain())) {

                if (!matchPassword(request.senha(), cliente.senha())) {
                    throw new RuntimeException("Credenciais inválidas");
                }

                // Cria sessão identificando como CLIENTE
                sessionId = sessionManager.createSession(cliente.id(), TipoUsuario.CLIENTE);
            }


        } else if (TipoUsuario.EMPRESA.equals(request.tipo())) {
            BuscarEmpresaOutput empresa = empresaFacade.buscarPorEmail(request.email());

            if (BcbEntityValidator.isEntityNotNull(empresa.toDomain())) {
                if (!matchPassword(request.senha(), empresa.senha())) {
                    throw new RuntimeException("Credenciais inválidas");
                }

                // Cria sessão identificando como EMPRESA
                sessionId = sessionManager.createSession(empresa.id(), TipoUsuario.EMPRESA);
            }

        }

        return sessionId;
    }

}
