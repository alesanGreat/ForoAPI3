package com.alura.foroapi;

import com.alura.foroapi.dto.TopicoDTO;
import com.alura.foroapi.dto.UsuarioDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTests extends BaseTestConfig {

    @Test
    void testFlujoCompleto() throws Exception {
        logger.info("Iniciando prueba: testFlujoCompleto");

        // 1. Registrar un nuevo usuario
        UsuarioDTO nuevoUsuario = new UsuarioDTO();
        nuevoUsuario.setNombre("Usuario Integración");
        nuevoUsuario.setEmail("integracion@example.com");
        nuevoUsuario.setPassword("password123");

        MvcResult registroResult = performRequest(mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoUsuario))))
                .andExpect(status().isCreated())
                .andReturn();

        // 2. Iniciar sesión con el nuevo usuario
        MvcResult loginResult = performRequest(mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoUsuario))))
                .andExpect(status().isOk())
                .andReturn();

        String token = objectMapper.readTree(loginResult.getResponse().getContentAsString()).get("token").asText();

        // 3. Crear un nuevo tópico
        TopicoDTO nuevoTopico = new TopicoDTO();
        nuevoTopico.setTitulo("Tópico de Integración");
        nuevoTopico.setMensaje("Este es un tópico creado en la prueba de integración");

        MvcResult crearTopicoResult = performRequest(mockMvc.perform(post("/topicos")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoTopico))))
                .andExpect(status().isCreated())
                .andReturn();

        Long topicoId = objectMapper.readTree(crearTopicoResult.getResponse().getContentAsString()).get("id").asLong();

        // 4. Obtener el tópico por ID
        performRequest(mockMvc.perform(get("/topicos/" + topicoId)
                .header("Authorization", "Bearer " + token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Tópico de Integración"));

        // 5. Actualizar el tópico
        nuevoTopico.setTitulo("Tópico de Integración Actualizado");
        performRequest(mockMvc.perform(put("/topicos/" + topicoId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoTopico))))
                .andExpect(status().isOk());

        // 6. Verificar la actualización
        performRequest(mockMvc.perform(get("/topicos/" + topicoId)
                .header("Authorization", "Bearer " + token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Tópico de Integración Actualizado"));

        // 7. Eliminar el tópico
        performRequest(mockMvc.perform(delete("/topicos/" + topicoId)
                .header("Authorization", "Bearer " + token)))
                .andExpect(status().isNoContent());

        // 8. Verificar que el tópico ha sido eliminado
        performRequest(mockMvc.perform(get("/topicos/" + topicoId)
                .header("Authorization", "Bearer " + token)))
                .andExpect(status().isNotFound());

        logger.info("Prueba testFlujoCompleto completada");
    }

    @Test
    void testValidacionDatos() throws Exception {
        logger.info("Iniciando prueba: testValidacionDatos");
        TopicoDTO topicoInvalido = new TopicoDTO();
        topicoInvalido.setTitulo("");
        topicoInvalido.setMensaje("");

        performRequest(mockMvc.perform(post("/topicos")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(topicoInvalido))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Error"));
        logger.info("Prueba testValidacionDatos completada");
    }
}