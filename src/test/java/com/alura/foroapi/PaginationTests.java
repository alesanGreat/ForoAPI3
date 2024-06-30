package com.alura.foroapi;

import com.alura.foroapi.dto.TopicoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PaginationTests extends BaseTestConfig {

    @BeforeEach
    void setUpPaginationTests() throws Exception {
        for (int i = 0; i < 25; i++) {
            TopicoDTO topicoDTO = new TopicoDTO();
            topicoDTO.setTitulo("Tópico " + i);
            topicoDTO.setMensaje("Contenido del tópico " + i);

            performRequest(mockMvc.perform(post("/topicos")
                    .header("Authorization", "Bearer " + authToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(topicoDTO))))
                    .andExpect(status().isCreated());
        }
    }

    @Test
    void testPaginacion() throws Exception {
        logger.info("Iniciando prueba: testPaginacion");
        performRequest(mockMvc.perform(get("/topicos?page=0&size=10")
                .header("Authorization", "Bearer " + authToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(25))
                .andExpect(jsonPath("$.totalPages").value(3));
        logger.info("Prueba testPaginacion completada");
    }

    @Test
    void testPaginacionUltimaPagina() throws Exception {
        logger.info("Iniciando prueba: testPaginacionUltimaPagina");
        performRequest(mockMvc.perform(get("/topicos?page=2&size=10")
                .header("Authorization", "Bearer " + authToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(5))
                .andExpect(jsonPath("$.totalElements").value(25))
                .andExpect(jsonPath("$.totalPages").value(3));
        logger.info("Prueba testPaginacionUltimaPagina completada");
    }
}
