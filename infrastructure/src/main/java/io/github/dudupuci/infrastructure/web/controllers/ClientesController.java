package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.infrastructure.web.controllers.apidocs.ClientesControllerAPI;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/clientes")
public class ClientesController implements ClientesControllerAPI {
}
