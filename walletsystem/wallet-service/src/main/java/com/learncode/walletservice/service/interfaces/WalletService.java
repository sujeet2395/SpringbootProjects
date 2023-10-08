package com.learncode.walletservice.service.interfaces;

import com.learncode.walletservice.request.AddMoneyIntoWalletRequest;
import com.learncode.walletservice.request.TransactionWalletRequest;
import com.learncode.walletservice.request.UpdateWalletRequest;
import com.learncode.walletservice.request.WalletRequest;
import com.learncode.walletservice.response.TransactionWalletStatus;
import com.learncode.walletservice.response.WalletResponse;

public interface WalletService {
    WalletResponse createWallet(WalletRequest walletRequest);

    WalletResponse updateWallet(Long walletId, UpdateWalletRequest updateWalletRequest);

    WalletResponse addMoneyIntoWallet(Long walletId, AddMoneyIntoWalletRequest addMoneyIntoWalletRequest);

    TransactionWalletStatus transactionWallet(TransactionWalletRequest transactionWalletRequest);

    void deleteWallet(Long walletId);

    WalletResponse getWalletById(Long walletId);

    WalletResponse getWalletByUsername(String username);
}