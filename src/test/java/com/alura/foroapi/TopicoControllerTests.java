package com.alura.foroapi;

import com.alura.foroapi.dto.TopicoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TopicoControllerTests extends BaseTestConfig {

    @Test
    void testListarTopicos() throws Exception {
        logger.info("Iniciando prueba: testListarTopicos");
        performRequest(mockMvc.perform(get("/topicos")
                .header("Authorization", "Bearer " + authToken)))
                .andExpect(status().isOk());
        logger.info("Prueba testListarTopicos completada");
    }

    @Test
    void testCrearTopico() throws Exception {
        logger.info("Iniciando prueba: testCrearTopico");
        TopicoDTO topicoDTO = new TopicoDTO();
        topicoDTO.setTitulo("Nuevo Tópico");
        topicoDTO.setMensaje("Contenido del nuevo tópico");

        performRequest(mockMvc.perform(post("/topicos")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(topicoDTO))))
                .andExpect(status().isCreated());
        logger.info("Prueba testCrearTopico completada");
    }

    @Test
    void testObtenerTopico() throws Exception {
        logger.info("Iniciando prueba: testObtenerTopico");
        TopicoDTO topicoDTO = new TopicoDTO();
        topicoDTO.setTitulo("Tópico para obtener");
        topicoDTO.setMensaje("Contenido del tópico");

        MvcResult createResult = performRequest(mockMvc.perform(post("/topicos")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(topicoDTO))))
                .andExpect(status().isCreated())
                .andReturn();

        TopicoDTO createdTopico = objectMapper.readValue(createResult.getResponse().getContentAsString(), TopicoDTO.class);

        performRequest(mockMvc.perform(get("/topicos/" + createdTopico.getId())
                .header("Authorization", "Bearer " + authToken)))
                .andExpect(status().isOk());
        logger.info("Prueba testObtenerTopico completada");
    }

    @Test
    void testActualizarTopico() throws Exception {
        logger.info("Iniciando prueba: testActualizarTopico");
        TopicoDTO topicoDTO = new TopicoDTO();
        topicoDTO.setTitulo("Tópico original");
        topicoDTO.setMensaje("Contenido original");

        MvcResult createResult = performRequest(mockMvc.perform(post("/topicos")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(topicoDTO))))
                .andExpect(status().isCreated())
                .andReturn();

        TopicoDTO createdTopico = objectMapper.readValue(createResult.getResponse().getContentAsString(), TopicoDTO.class);

        topicoDTO.setTitulo("Tópico actualizado");
        topicoDTO.setMensaje("Contenido actualizado");

        performRequest(mockMvc.perform(put("/topicos/" + createdTopico.getId())
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(topicoDTO))))
                .andExpect(status().isOk());
        logger.info("Prueba testActualizarTopico completada");
    }

    @Test
    void testEliminarTopico() throws Exception {
        logger.info("Iniciando prueba: testEliminarTopico");
        TopicoDTO topicoDTO = new TopicoDTO();
        topicoDTO.setTitulo("Tópico para eliminar");
        topicoDTO.setMensaje("Contenido del tópico");

        MvcResult createResult = performRequest(mockMvc.perform(post("/topicos")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(topicoDTO))))
                .andExpect(status().isCreated())
                .andReturn();

        TopicoDTO createdTopico = objectMapper.readValue(createResult.getResponse().getContentAsString(), TopicoDTO.class);

        performRequest(mockMvc.perform(delete("/topicos/" + createdTopico.getId())
                .header("Authorization", "Bearer " + authToken)))
                .andExpect(status().isNoContent());
        logger.info("Prueba testEliminarTopico completada");
    }
}