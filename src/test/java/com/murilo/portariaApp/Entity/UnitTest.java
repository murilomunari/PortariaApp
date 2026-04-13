package com.murilo.portariaApp.Entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UnitTest {

    @Test
    void shouldBuildUnitWithAllFields() {
        UUID id = UUID.randomUUID();

        Unit unit = Unit.builder()
                .id(id)
                .block("B")
                .number("203")
                .floor(2)
                .description("Apartamento de frente")
                .build();

        assertEquals(id, unit.getId());
        assertEquals("B", unit.getBlock());
        assertEquals("203", unit.getNumber());
        assertEquals(2, unit.getFloor());
        assertEquals("Apartamento de frente", unit.getDescription());
    }

    @Test
    void shouldSetFieldsUsingSetters() {
        UUID id = UUID.randomUUID();
        Unit unit = new Unit();

        unit.setId(id);
        unit.setBlock("A");
        unit.setNumber("101");
        unit.setFloor(1);
        unit.setDescription("Perto da portaria");

        assertEquals(id, unit.getId());
        assertEquals("A", unit.getBlock());
        assertEquals("101", unit.getNumber());
        assertEquals(1, unit.getFloor());
        assertEquals("Perto da portaria", unit.getDescription());
    }

    @Test
    void shouldCreateAllArgsConstructorAndNoArgsConstructor() {
        UUID id = UUID.randomUUID();
        Unit allArgs = new Unit(id, "C", "301", 3, "Cobertura");

        assertEquals(id, allArgs.getId());
        assertEquals("C", allArgs.getBlock());
        assertEquals("301", allArgs.getNumber());
        assertEquals(3, allArgs.getFloor());
        assertEquals("Cobertura", allArgs.getDescription());

        Unit noArgs = new Unit();

        assertNull(noArgs.getId());
        assertNull(noArgs.getBlock());
        assertNull(noArgs.getNumber());
        assertNull(noArgs.getFloor());
        assertNull(noArgs.getDescription());
    }
}
