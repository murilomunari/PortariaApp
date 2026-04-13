package com.murilo.portariaApp.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest

@Transactional
class UnitControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;


    private MockMvc mockMvc;

    @Test
    void shouldCreateUnitAndFindByNumber() throws Exception {
        var createPayload = """
                {
                  "number": "101",
                  "block": "A",
                  "floor": 1,
                  "description": "Unidade perto da portaria"
                }
                """;

        mockMvc.perform(post("/v1/unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.number").value("101"))
                .andExpect(jsonPath("$.block").value("A"))
                .andExpect(jsonPath("$.floor").value(1))
                .andExpect(jsonPath("$.description").value("Unidade perto da portaria"));

        mockMvc.perform(get("/v1/unit/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].number").value("101"))
                .andExpect(jsonPath("$[0].block").value("A"));
    }

    @Test
    void shouldReturnBadRequestWhenUnitNumberIsBlank() throws Exception {
        var invalidPayload = """
                {
                  "number": "",
                  "block": "A",
                  "floor": 2,
                  "description": "Payload inválido"
                }
                """;

        mockMvc.perform(post("/v1/unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload))
                .andExpect(status().isBadRequest());
    }
}
