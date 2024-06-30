package com.alura.foroapi;

import com.alura.foroapi.dto.LoginDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthenticationTests extends BaseTestConfig {

    @Test
    void testAutenticacionExitosa() throws Exception {
        logger.info("Iniciando prueba: testAutenticacionExitosa");
        LoginDTO loginDTO = new LoginDTO("test@example.com", "password");
        performRequest(mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
        logger.info("Prueba testAutenticacionExitosa completada");
    }

    @Test
    void testAutenticacionFallida() throws Exception {
        logger.info("Iniciando prueba: testAutenticacionFallida");
        LoginDTO loginDTO = new LoginDTO("usuario_inexistente@example.com", "passwordincorrecto");
        performRequest(mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO))))
                .andExpect(status().isUnauthorized());
        logger.info("Prueba testAutenticacionFallida completada");
    }

    @Test
    void testAccesoNoAutorizado() throws Exception {
        logger.info("Iniciando prueba: testAccesoNoAutorizado");
        performRequest(mockMvc.perform(get("/topicos")))
                .andExpect(status().isUnauthorized());
        logger.info("Prueba testAccesoNoAutorizado completada");
    }
}
