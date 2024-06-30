package com.alura.foroapi;

import com.alura.foroapi.dto.UsuarioDTO;
import com.alura.foroapi.model.Usuario;
import com.alura.foroapi.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTests extends BaseTestConfig {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void testRegistrarUsuario() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre("Nuevo Usuario");
        usuarioDTO.setEmail("nuevo@example.com");
        usuarioDTO.setPassword("nuevacontrasena");

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setId(1L);
        nuevoUsuario.setNombre(usuarioDTO.getNombre());
        nuevoUsuario.setEmail(usuarioDTO.getEmail());

        when(usuarioService.registrarUsuario(any(UsuarioDTO.class))).thenReturn(nuevoUsuario);

        performRequest(mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO))))
                .andExpect(status().isCreated());
    }

    @Test
    void testObtenerUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Usuario Test");
        usuario.setEmail("test@example.com");

        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(usuario);

        performRequest(mockMvc.perform(get("/usuarios/1")
                .header("Authorization", "Bearer " + authToken)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarUsuario() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre("Usuario Actualizado");
        usuarioDTO.setEmail("actualizado@example.com");

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(1L);
        usuarioActualizado.setNombre(usuarioDTO.getNombre());
        usuarioActualizado.setEmail(usuarioDTO.getEmail());

        when(usuarioService.actualizarUsuario(any(Long.class), any(UsuarioDTO.class))).thenReturn(usuarioActualizado);

        performRequest(mockMvc.perform(put("/usuarios/1")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO))))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarUsuario() throws Exception {
        when(usuarioService.existeUsuario(1L)).thenReturn(true);

        performRequest(mockMvc.perform(delete("/usuarios/1")
                .header("Authorization", "Bearer " + authToken)))
                .andExpect(status().isNoContent());
    }
}