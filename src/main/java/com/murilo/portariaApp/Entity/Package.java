package com.murilo.portariaApp.Entity;

import com.murilo.portariaApp.enums.PackageStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "packages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String description;

    @Column(nullable = false, length = 150)
    private String sender;

    @Column(length = 100, unique = true)
    private String trackingCode;

    @Column(nullable = false)
    private LocalDateTime receivedAt;

    private LocalDateTime pickedUpAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PackageStatus status;

    @Column(length = 255)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

    @ManyToOne
    @JoinColumn(name = "received_by", nullable = false)
    private User receivedBy;

    @ManyToOne
    @JoinColumn(name = "delivered_by")
    private User deliveredBy;
}