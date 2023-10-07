package com.learncode.transactionservice.response;

import com.learncode.transactionservice.apiconstant.TransactionStatus;
import com.learncode.transactionservice.apiconstant.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {

    private String transactionId;

    private double amount;

    private String fromUser;

    private String toUser;

    private TransactionStatus transactionStatus;
}
