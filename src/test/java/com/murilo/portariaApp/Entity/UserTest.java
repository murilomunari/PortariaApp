package com.murilo.portariaApp.Entity;

import com.murilo.portariaApp.enums.Role;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserTest {

    @Test
    void shouldBuildUserWithAllFields() {
        UUID id = UUID.randomUUID();

        User user = User.builder()
                .id(id)
                .name("Murilo")
                .email("murilo@email.com")
                .password("senha123")
                .role(Role.ADMIN)
                .active(true)
                .build();

        assertEquals(id, user.getId());
        assertEquals("Murilo", user.getName());
        assertEquals("murilo@email.com", user.getEmail());
        assertEquals("senha123", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
        assertEquals(true, user.getActive());
    }

    @Test
    void shouldSetFieldsUsingSetters() {
        UUID id = UUID.randomUUID();
        User user = new User();

        user.setId(id);
        user.setName("Ana");
        user.setEmail("ana@email.com");
        user.setPassword("abc123");
        user.setRole(Role.PORTEIRO);
        user.setActive(false);

        assertEquals(id, user.getId());
        assertEquals("Ana", user.getName());
        assertEquals("ana@email.com", user.getEmail());
        assertEquals("abc123", user.getPassword());
        assertEquals(Role.PORTEIRO, user.getRole());
        assertEquals(false, user.getActive());
    }

    @Test
    void shouldCreateAllArgsConstructorAndNoArgsConstructor() {
        UUID id = UUID.randomUUID();
        User allArgs = new User(id, "Carlos", "carlos@email.com", "123456", Role.MORADOR, true);

        assertEquals(id, allArgs.getId());
        assertEquals("Carlos", allArgs.getName());
        assertEquals("carlos@email.com", allArgs.getEmail());
        assertEquals("123456", allArgs.getPassword());
        assertEquals(Role.MORADOR, allArgs.getRole());
        assertEquals(true, allArgs.getActive());

        User noArgs = new User();

        assertNull(noArgs.getId());
        assertNull(noArgs.getName());
        assertNull(noArgs.getEmail());
        assertNull(noArgs.getPassword());
        assertNull(noArgs.getRole());
        assertNull(noArgs.getActive());
    }
}
