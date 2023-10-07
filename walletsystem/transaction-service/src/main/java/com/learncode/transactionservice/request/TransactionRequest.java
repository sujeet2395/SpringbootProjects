package com.learncode.transactionservice.request;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {

    private double amount;

    private String fromUser;

    private String toUser;

    private TransactionType transactionType;
}
