package com.learncode.walletservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionWalletRequest {
    String fromUsername;

    String toUsername;

    private Double amount;
}