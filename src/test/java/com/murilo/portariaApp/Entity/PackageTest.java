package com.murilo.portariaApp.Entity;

import com.murilo.portariaApp.enums.PackageStatus;
import com.murilo.portariaApp.enums.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PackageTest {

    @Test
    void shouldBuildPackageWithAllFields() {
        UUID id = UUID.randomUUID();
        LocalDateTime receivedAt = LocalDateTime.now().minusDays(1);
        LocalDateTime pickedUpAt = LocalDateTime.now();
        Resident resident = buildResident();
        User receivedBy = User.builder().id(UUID.randomUUID()).name("Porteiro 1").role(Role.PORTEIRO).active(true).build();
        User deliveredBy = User.builder().id(UUID.randomUUID()).name("Porteiro 2").role(Role.PORTEIRO).active(true).build();

        Package pkg = Package.builder()
                .id(id)
                .description("Caixa grande")
                .sender("Loja XYZ")
                .trackingCode("BR123456789")
                .receivedAt(receivedAt)
                .pickedUpAt(pickedUpAt)
                .status(PackageStatus.PICKED_UP)
                .notes("Entregue sem avarias")
                .resident(resident)
                .receivedBy(receivedBy)
                .deliveredBy(deliveredBy)
                .build();

        assertEquals(id, pkg.getId());
        assertEquals("Caixa grande", pkg.getDescription());
        assertEquals("Loja XYZ", pkg.getSender());
        assertEquals("BR123456789", pkg.getTrackingCode());
        assertEquals(receivedAt, pkg.getReceivedAt());
        assertEquals(pickedUpAt, pkg.getPickedUpAt());
        assertEquals(PackageStatus.PICKED_UP, pkg.getStatus());
        assertEquals("Entregue sem avarias", pkg.getNotes());
        assertEquals(resident, pkg.getResident());
        assertEquals(receivedBy, pkg.getReceivedBy());
        assertEquals(deliveredBy, pkg.getDeliveredBy());
    }

    @Test
    void shouldSetFieldsUsingSetters() {
        UUID id = UUID.randomUUID();
        LocalDateTime receivedAt = LocalDateTime.now().minusHours(2);
        LocalDateTime pickedUpAt = LocalDateTime.now();
        Resident resident = buildResident();
        User receivedBy = User.builder().id(UUID.randomUUID()).name("Porteiro A").role(Role.PORTEIRO).active(true).build();
        User deliveredBy = User.builder().id(UUID.randomUUID()).name("Porteiro B").role(Role.PORTEIRO).active(true).build();
        Package pkg = new Package();

        pkg.setId(id);
        pkg.setDescription("Envelope");
        pkg.setSender("Banco ABC");
        pkg.setTrackingCode("BR987654321");
        pkg.setReceivedAt(receivedAt);
        pkg.setPickedUpAt(pickedUpAt);
        pkg.setStatus(PackageStatus.STORED);
        pkg.setNotes("Guardar no armario");
        pkg.setResident(resident);
        pkg.setReceivedBy(receivedBy);
        pkg.setDeliveredBy(deliveredBy);

        assertEquals(id, pkg.getId());
        assertEquals("Envelope", pkg.getDescription());
        assertEquals("Banco ABC", pkg.getSender());
        assertEquals("BR987654321", pkg.getTrackingCode());
        assertEquals(receivedAt, pkg.getReceivedAt());
        assertEquals(pickedUpAt, pkg.getPickedUpAt());
        assertEquals(PackageStatus.STORED, pkg.getStatus());
        assertEquals("Guardar no armario", pkg.getNotes());
        assertEquals(resident, pkg.getResident());
        assertEquals(receivedBy, pkg.getReceivedBy());
        assertEquals(deliveredBy, pkg.getDeliveredBy());
    }

    @Test
    void shouldCreateAllArgsConstructorAndNoArgsConstructor() {
        UUID id = UUID.randomUUID();
        LocalDateTime receivedAt = LocalDateTime.now().minusMinutes(30);
        LocalDateTime pickedUpAt = LocalDateTime.now();
        Resident resident = buildResident();
        User receivedBy = User.builder().id(UUID.randomUUID()).name("Porteiro C").role(Role.PORTEIRO).active(true).build();
        User deliveredBy = User.builder().id(UUID.randomUUID()).name("Porteiro D").role(Role.PORTEIRO).active(true).build();
        Package allArgs = new Package(
                id,
                "Documento",
                "Cartorio",
                "TRK001",
                receivedAt,
                pickedUpAt,
                PackageStatus.RECEIVED,
                "Assinar recebimento",
                resident,
                receivedBy,
                deliveredBy
        );

        assertEquals(id, allArgs.getId());
        assertEquals("Documento", allArgs.getDescription());
        assertEquals("Cartorio", allArgs.getSender());
        assertEquals("TRK001", allArgs.getTrackingCode());
        assertEquals(receivedAt, allArgs.getReceivedAt());
        assertEquals(pickedUpAt, allArgs.getPickedUpAt());
        assertEquals(PackageStatus.RECEIVED, allArgs.getStatus());
        assertEquals("Assinar recebimento", allArgs.getNotes());
        assertEquals(resident, allArgs.getResident());
        assertEquals(receivedBy, allArgs.getReceivedBy());
        assertEquals(deliveredBy, allArgs.getDeliveredBy());

        Package noArgs = new Package();

        assertNull(noArgs.getId());
        assertNull(noArgs.getDescription());
        assertNull(noArgs.getSender());
        assertNull(noArgs.getTrackingCode());
        assertNull(noArgs.getReceivedAt());
        assertNull(noArgs.getPickedUpAt());
        assertNull(noArgs.getStatus());
        assertNull(noArgs.getNotes());
        assertNull(noArgs.getResident());
        assertNull(noArgs.getReceivedBy());
        assertNull(noArgs.getDeliveredBy());
    }

    private Resident buildResident() {
        Unit unit = Unit.builder().id(UUID.randomUUID()).block("A").number("101").floor(1).description("Unidade teste").build();
        return Resident.builder()
                .id(UUID.randomUUID())
                .name("Morador Teste")
                .phone("11000000000")
                .email("morador@email.com")
                .active(true)
                .unit(unit)
                .build();
    }
}
