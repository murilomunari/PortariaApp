package com.murilo.portariaApp.integration;

import com.murilo.portariaApp.Entity.Unit;
import com.murilo.portariaApp.repository.UnitRepository;
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
class ResidentControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;


    private MockMvc mockMvc;

    @Autowired
    private UnitRepository unitRepository;

    @Test
    void shouldCreateResidentAndFindByUnit() throws Exception {
        Unit unit = unitRepository.save(Unit.builder()
                .number("202")
                .block("B")
                .floor(2)
                .description("Unidade para teste de morador")
                .build());

        var createPayload = """
                {
                  "name": "Joao da Silva",
                  "phone": "11999998888",
                  "email": "joao.silva@portaria.com",
                  "unitId": "%s"
                }
                """.formatted(unit.getId());

        mockMvc.perform(post("/v1/resident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Joao da Silva"))
                .andExpect(jsonPath("$.email").value("joao.silva@portaria.com"))
                .andExpect(jsonPath("$.unitId").value(unit.getId().toString()))
                .andExpect(jsonPath("$.active").value(true));

        mockMvc.perform(get("/v1/resident/unit/{unitId}", unit.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Joao da Silva"))
                .andExpect(jsonPath("$[0].unitId").value(unit.getId().toString()));
    }
}
