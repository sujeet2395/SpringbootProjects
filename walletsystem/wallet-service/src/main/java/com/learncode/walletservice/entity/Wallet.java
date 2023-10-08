package com.learncode.walletservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "active")
    private Boolean active;

    @CreationTimestamp
    @Column(name = "wallet_created_at", updatable = false)
    private LocalDateTime walletCreatedAt;

    @UpdateTimestamp
    @Column(name = "wallet_updated_at")
    private LocalDateTime walletUpdatedAt;
}
