package com.murilo.portariaApp.integration;

import com.murilo.portariaApp.Entity.User;
import com.murilo.portariaApp.enums.Role;
import com.murilo.portariaApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void shouldCreateUserAndReturnInList() throws Exception {
        String createPayload = """
                {
                  "name": "Porteiro 1",
                  "email": "porteiro1@portaria.com",
                  "password": "senha123",
                  "role": "PORTEIRO",
                  "active": true
                }
                """;

        mockMvc.perform(post("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Porteiro 1"))
                .andExpect(jsonPath("$.email").value("porteiro1@portaria.com"))
                .andExpect(jsonPath("$.role").value("PORTEIRO"));

        mockMvc.perform(get("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Porteiro 1"))
                .andExpect(jsonPath("$[0].email").value("porteiro1@portaria.com"))
                .andExpect(jsonPath("$[0].role").value("PORTEIRO"));
    }

    @Test
    void shouldPatchUserNameAndEmail() throws Exception {
        User savedUser = userRepository.save(
                User.builder()
                        .name("Usuario Antigo")
                        .email("usuario.antigo@portaria.com")
                        .password("senha123")
                        .role(Role.ADMIN)
                        .active(true)
                        .build()
        );

        String patchPayload = """
                {
                  "name": "Usuario Novo",
                  "email": "usuario.novo@portaria.com"
                }
                """;

        mockMvc.perform(patch("/v1/user/{id}", savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Usuario Novo"))
                .andExpect(jsonPath("$.email").value("usuario.novo@portaria.com"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }
}