package io.github.dudupuci.infrastructure.persistence.facade.login;

import io.github.dudupuci.infrastructure.web.dtos.request.login.LoginApiRequest;
import io.github.dudupuci.infrastructure.web.dtos.response.login.LoginOutput;

public interface LoginFacade {
    LoginOutput doLogin(LoginApiRequest request);
}
