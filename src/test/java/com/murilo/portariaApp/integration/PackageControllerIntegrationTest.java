package com.murilo.portariaApp.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.murilo.portariaApp.Entity.Resident;
import com.murilo.portariaApp.Entity.Unit;
import com.murilo.portariaApp.Entity.User;
import com.murilo.portariaApp.enums.Role;
import com.murilo.portariaApp.repository.ResidentRepository;
import com.murilo.portariaApp.repository.UnitRepository;
import com.murilo.portariaApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class PackageControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;


    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ResidentRepository residentRepository;

    @Test
    void shouldCreatePackageAndPickupPackage() throws Exception {
        User receivedBy = userRepository.save(User.builder()
                .name("Porteiro Recebimento")
                .email("recebimento@portaria.com")
                .password("senha123")
                .role(Role.PORTEIRO)
                .active(true)
                .build());

        User deliveredBy = userRepository.save(User.builder()
                .name("Porteiro Entrega")
                .email("entrega@portaria.com")
                .password("senha123")
                .role(Role.PORTEIRO)
                .active(true)
                .build());

        Unit unit = unitRepository.save(Unit.builder()
                .number("303")
                .block("C")
                .floor(3)
                .description("Unidade para fluxo de encomenda")
                .build());

        Resident resident = residentRepository.save(Resident.builder()
                .name("Morador Teste")
                .phone("11977776666")
                .email("morador.teste@portaria.com")
                .active(true)
                .unit(unit)
                .build());

        var createPayload = """
                {
                  "description": "Caixa grande",
                  "sender": "Loja XYZ",
                  "residentId": "%s",
                  "receivedByUserId": "%s",
                  "trackingCode": "TRK-001",
                  "notes": "Deixar na prateleira principal"
                }
                """.formatted(resident.getId(), receivedBy.getId());

        MvcResult createResult = mockMvc.perform(post("/v1/package")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.status").value("RECEIVED"))
                .andExpect(jsonPath("$.residentId").value(resident.getId().toString()))
                .andExpect(jsonPath("$.receivedByUserId").value(receivedBy.getId().toString()))
                .andReturn();

        JsonNode responseBody = objectMapper.readTree(createResult.getResponse().getContentAsString());
        UUID packageId = UUID.fromString(responseBody.get("id").asText());

        var pickupPayload = """
                {
                  "deliveredByUserId": "%s"
                }
                """.formatted(deliveredBy.getId());

        mockMvc.perform(patch("/v1/package/{id}/pickup", packageId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pickupPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PICKED_UP"))
                .andExpect(jsonPath("$.deliveredByUserId").value(deliveredBy.getId().toString()))
                .andExpect(jsonPath("$.deliveredByUserName").value("Porteiro Entrega"));

        mockMvc.perform(get("/v1/package/{id}", packageId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(packageId.toString()))
                .andExpect(jsonPath("$.status").value("PICKED_UP"));
    }
}
