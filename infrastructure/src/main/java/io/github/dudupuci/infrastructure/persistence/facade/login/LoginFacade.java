package io.github.dudupuci.infrastructure.persistence.facade.login;

import io.github.dudupuci.infrastructure.web.dtos.request.login.LoginRequest;

public interface LoginFacade {
    String doLogin(LoginRequest request);
}
