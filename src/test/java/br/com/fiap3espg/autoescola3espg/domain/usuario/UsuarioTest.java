
package br.com.fiap3espg.autoescola3espg.domain.usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsuarioTest {

    @Test
    void deveRetornarLoginESenha() {

        Usuario usuario = new Usuario(
                1L,
                "admin",
                "senhaHash",
                Role.ADMIN
        );

        assertEquals("admin", usuario.getUsername());
        assertEquals("senhaHash", usuario.getPassword());
    }

    @Test
    void deveRetornarPermissaoAdmin() {

        Usuario usuario = new Usuario(
                1L,
                "admin",
                "senhaHash",
                Role.ADMIN
        );

        boolean possuiPermissao = usuario.getAuthorities()
                .stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_ADMIN")
                );

        assertTrue(possuiPermissao);
        assertTrue(usuario.isEnabled());
    }
}
