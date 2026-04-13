package com.murilo.portariaApp.Entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ResidentTest {

    @Test
    void shouldBuildResidentWithAllFields() {
        UUID id = UUID.randomUUID();
        Unit unit = Unit.builder().id(UUID.randomUUID()).block("A").number("101").floor(1).description("Teste").build();

        Resident resident = Resident.builder()
                .id(id)
                .name("Joao Silva")
                .phone("11999990000")
                .email("joao@email.com")
                .active(true)
                .unit(unit)
                .build();

        assertEquals(id, resident.getId());
        assertEquals("Joao Silva", resident.getName());
        assertEquals("11999990000", resident.getPhone());
        assertEquals("joao@email.com", resident.getEmail());
        assertEquals(true, resident.getActive());
        assertEquals(unit, resident.getUnit());
    }

    @Test
    void shouldSetFieldsUsingSetters() {
        UUID id = UUID.randomUUID();
        Unit unit = new Unit(UUID.randomUUID(), "B", "202", 2, "Teste 2");
        Resident resident = new Resident();

        resident.setId(id);
        resident.setName("Maria Souza");
        resident.setPhone("11888887777");
        resident.setEmail("maria@email.com");
        resident.setActive(false);
        resident.setUnit(unit);

        assertEquals(id, resident.getId());
        assertEquals("Maria Souza", resident.getName());
        assertEquals("11888887777", resident.getPhone());
        assertEquals("maria@email.com", resident.getEmail());
        assertEquals(false, resident.getActive());
        assertEquals(unit, resident.getUnit());
    }

    @Test
    void shouldCreateAllArgsConstructorAndNoArgsConstructor() {
        UUID id = UUID.randomUUID();
        Unit unit = Unit.builder().id(UUID.randomUUID()).block("C").number("303").floor(3).description("Teste 3").build();
        Resident allArgs = new Resident(id, "Carlos Lima", "11777776666", "carlos@email.com", true, unit);

        assertEquals(id, allArgs.getId());
        assertEquals("Carlos Lima", allArgs.getName());
        assertEquals("11777776666", allArgs.getPhone());
        assertEquals("carlos@email.com", allArgs.getEmail());
        assertEquals(true, allArgs.getActive());
        assertEquals(unit, allArgs.getUnit());

        Resident noArgs = new Resident();

        assertNull(noArgs.getId());
        assertNull(noArgs.getName());
        assertNull(noArgs.getPhone());
        assertNull(noArgs.getEmail());
        assertNull(noArgs.getActive());
        assertNull(noArgs.getUnit());
    }
}
