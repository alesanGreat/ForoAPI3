package com.alura.foroapi;

import com.alura.foroapi.dto.LoginDTO;
import com.alura.foroapi.dto.TokenDTO;
import com.alura.foroapi.model.Usuario;
import com.alura.foroapi.repository.TopicoRepository;
import com.alura.foroapi.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class BaseTestConfig {

    protected static final Logger logger = LoggerFactory.getLogger(BaseTestConfig.class);

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Autowired
    protected TopicoRepository topicoRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected String authToken;

    @BeforeEach
    void setUp() throws Exception {
        logger.info("Iniciando configuración de pruebas");
        topicoRepository.deleteAll();
        usuarioRepository.deleteAll();

        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario Test");
        usuario.setEmail("test@example.com");
        usuario.setPassword(passwordEncoder.encode("password"));
        usuarioRepository.save(usuario);
        logger.info("Usuario de prueba creado: {}", usuario.getEmail());

        LoginDTO loginDTO = new LoginDTO("test@example.com", "password");
        MvcResult result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andReturn();

        if (result.getResponse().getStatus() == 200) {
            TokenDTO tokenDTO = objectMapper.readValue(result.getResponse().getContentAsString(), TokenDTO.class);
            authToken = tokenDTO.getToken();
            logger.info("Token de autenticación obtenido con éxito");
        } else {
            logger.error("Error en autenticación. Código de estado: {}", result.getResponse().getStatus());
            logger.error("Respuesta de error: {}", result.getResponse().getContentAsString());
        }
    }

    protected ResultActions performRequest(ResultActions resultActions) throws Exception {
        MvcResult result = resultActions.andReturn();
        if (result.getResponse().getStatus() >= 400) {
            logger.error("Error en la respuesta. Código de estado: {}", result.getResponse().getStatus());
            logger.error("Cuerpo de la respuesta: {}", result.getResponse().getContentAsString());
        } else {
            logger.info("Respuesta exitosa. Código de estado: {}", result.getResponse().getStatus());
        }
        return resultActions;
    }
}
