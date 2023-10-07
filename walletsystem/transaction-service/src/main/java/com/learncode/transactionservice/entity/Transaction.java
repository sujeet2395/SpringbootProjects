package com.learncode.transactionservice.entity;

import com.learncode.transactionservice.apiconstant.TransactionStatus;
import com.learncode.transactionservice.apiconstant.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "from_user")
    private String fromUser;

    @Column(name = "to_user")
    private String toUser;

    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "transaction_status")
    private TransactionStatus transactionStatus;

    @CreationTimestamp
    @Column(name = "transaction_created_at", updatable = false)
    private LocalDateTime transactionCreatedAt;

    @UpdateTimestamp
    @Column(name = "transaction_updated_at")
    private LocalDateTime transactionUpdatedAt;
}
