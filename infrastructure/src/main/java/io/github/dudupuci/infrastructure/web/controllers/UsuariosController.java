package io.github.dudupuci.infrastructure.web.controllers;

import io.github.dudupuci.domain.entities.Cliente;
import io.github.dudupuci.domain.entities.Empresa;
import io.github.dudupuci.domain.enums.TipoUsuario;
import io.github.dudupuci.domain.repositories.ClienteRepository;
import io.github.dudupuci.domain.repositories.EmpresaRepository;
import io.github.dudupuci.infrastructure.security.annotations.RequiresAuth;
import io.github.dudupuci.infrastructure.web.dtos.response.usuario.BuscarUsuarioPorEmailApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller para operações gerais de usuários (Clientes e Empresas)
 */
@RestController
@RequestMapping("/usuarios")
@RequiresAuth
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuariosController {

    private static final Logger logger = LoggerFactory.getLogger(UsuariosController.class);

    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;

    public UsuariosController(ClienteRepository clienteRepository, EmpresaRepository empresaRepository) {
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
    }

    /**
     * GET /usuarios/buscar-por-email?email=exemplo@email.com
     * Busca um usuário (Cliente ou Empresa) pelo email
     */
    @GetMapping("/buscar-por-email")
    public ResponseEntity<?> buscarPorEmail(@RequestParam String email) {
        try {
            // Normaliza o email (trim e lowercase)
            String emailNormalizado = email.trim().toLowerCase();

            logger.info("🔍 Buscando usuário por email: '{}' (normalizado: '{}')", email, emailNormalizado);

            // Tenta buscar como cliente primeiro
            Optional<Cliente> clienteOpt = clienteRepository.buscarPorEmail(emailNormalizado);
            if (clienteOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                BuscarUsuarioPorEmailApiResponse response = new BuscarUsuarioPorEmailApiResponse(
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getEmail(),
                        TipoUsuario.CLIENTE
                );
                logger.info("✅ Cliente encontrado: {} (ID: {})", cliente.getNome(), cliente.getId());
                return ResponseEntity.ok(response);
            }

            logger.debug("Cliente não encontrado, buscando como empresa...");

            // Se não encontrou cliente, busca como empresa
            Optional<Empresa> empresaOpt = empresaRepository.buscarPorEmail(emailNormalizado);
            if (empresaOpt.isPresent()) {
                Empresa empresa = empresaOpt.get();
                BuscarUsuarioPorEmailApiResponse response = new BuscarUsuarioPorEmailApiResponse(
                        empresa.getId(),
                        empresa.getRazaoSocial(),
                        empresa.getEmail(),
                        TipoUsuario.EMPRESA
                );
                logger.info("✅ Empresa encontrada: {} (ID: {})", empresa.getRazaoSocial(), empresa.getId());
                return ResponseEntity.ok(response);
            }

            logger.warn("⚠️ Usuário não encontrado com email: '{}' (normalizado: '{}')", email, emailNormalizado);
            return ResponseEntity.status(404).body("Usuário não encontrado com o email: " + email);

        } catch (Exception e) {
            logger.error("❌ Erro ao buscar usuário por email: '{}' - Erro: {}", email, e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Erro ao buscar usuário: " + e.getMessage());
        }
    }
}

