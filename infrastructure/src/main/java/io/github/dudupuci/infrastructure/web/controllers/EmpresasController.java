package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.infrastructure.web.controllers.apidocs.ClientesControllerAPI;
import io.github.dudupuci.infrastructure.web.controllers.apidocs.EmpresasControllerAPI;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/empresas")
public class EmpresasController implements EmpresasControllerAPI {
}
