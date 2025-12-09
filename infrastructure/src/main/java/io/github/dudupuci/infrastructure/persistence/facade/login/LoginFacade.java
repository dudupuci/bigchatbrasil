package io.github.dudupuci.infrastructure.persistence.facade.login;

import io.github.dudupuci.infrastructure.web.dtos.request.login.LoginApiRequest;

public interface LoginFacade {
    String doLogin(LoginApiRequest request);
}
