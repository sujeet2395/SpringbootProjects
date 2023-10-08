package com.learncode.walletservice.request;

import com.learncode.walletservice.entity.Wallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletRequest {
    private Double amount;

    private String username;

    private Boolean active;
}

