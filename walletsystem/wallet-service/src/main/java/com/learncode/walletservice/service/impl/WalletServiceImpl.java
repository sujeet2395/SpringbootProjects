package com.learncode.walletservice.service.impl;

import com.learncode.walletservice.apiconstant.Constant;
import com.learncode.walletservice.entity.Wallet;
import com.learncode.walletservice.exception.InsufficientBalanceException;
import com.learncode.walletservice.exception.ResourceNotFoundException;
import com.learncode.walletservice.repository.WalletRepository;
import com.learncode.walletservice.request.AddMoneyIntoWalletRequest;
import com.learncode.walletservice.request.TransactionWalletRequest;
import com.learncode.walletservice.request.UpdateWalletRequest;
import com.learncode.walletservice.request.WalletRequest;
import com.learncode.walletservice.response.TransactionWalletStatus;
import com.learncode.walletservice.response.WalletResponse;
import com.learncode.walletservice.service.interfaces.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public WalletResponse createWallet(WalletRequest walletRequest) {
        Wallet wallet = this.mapWalletRequestToWallet(walletRequest);
        WalletResponse walletResponse = this.mapWalletToWalletResponse(walletRepository.save(wallet));
        return walletResponse;
    }



    @Override
    public WalletResponse updateWallet(Long walletId, UpdateWalletRequest updateWalletRequest) {
        Wallet walletInstanceFromDB = walletRepository.findById(walletId).orElseThrow(() -> new ResourceNotFoundException("Wallet is not found."));
        if(Objects.nonNull(updateWalletRequest.getUsername()) && !updateWalletRequest.getUsername().isEmpty())
            walletInstanceFromDB.setUsername(updateWalletRequest.getUsername());
        if(Objects.nonNull(updateWalletRequest.getActive()))
            walletInstanceFromDB.setActive(updateWalletRequest.getActive());
        return this.mapWalletToWalletResponse(walletRepository.save(walletInstanceFromDB));
    }

    @Override
    public WalletResponse addMoneyIntoWallet(Long walletId, AddMoneyIntoWalletRequest addMoneyIntoWalletRequest) {
        Wallet walletInstanceFromDB = walletRepository.findById(walletId).orElseThrow(() -> new ResourceNotFoundException("Wallet is not found."));
        if(!walletInstanceFromDB.getActive())
            throw new RuntimeException("Wallet is inactive.");
        if(Objects.nonNull(addMoneyIntoWalletRequest.getAmount()) && addMoneyIntoWalletRequest.getAmount()>=0d)
            walletInstanceFromDB.setAmount(addMoneyIntoWalletRequest.getAmount());
        return this.mapWalletToWalletResponse(walletRepository.save(walletInstanceFromDB));
    }

    @Override
    @Transactional
    public TransactionWalletStatus transactionWallet(TransactionWalletRequest transactionWalletRequest) {
        Wallet fromWalletInstanceFromDB = walletRepository.findByUsernameAndActive(transactionWalletRequest.getFromUsername(), true).orElseThrow(() -> new ResourceNotFoundException("Sender Wallet is not found with associated username."));
        Wallet toWalletInstanceFromDB = walletRepository.findByUsernameAndActive(transactionWalletRequest.getToUsername(), true).orElseThrow(() -> new ResourceNotFoundException("Receiver Wallet is not found with associated username."));
        TransactionWalletStatus transactionWalletStatus = null;
        try {
            if (Objects.isNull(transactionWalletRequest.getAmount()) || transactionWalletRequest.getAmount() <= 0d) {
                throw new IllegalArgumentException("Amount is not correct.");
            }
            if (fromWalletInstanceFromDB.getAmount() < transactionWalletRequest.getAmount()) {
                throw new InsufficientBalanceException("Balance is not sufficient.");
            }
            fromWalletInstanceFromDB.setAmount(fromWalletInstanceFromDB.getAmount() - transactionWalletRequest.getAmount());
            toWalletInstanceFromDB.setAmount(toWalletInstanceFromDB.getAmount() + transactionWalletRequest.getAmount());
            walletRepository.save(fromWalletInstanceFromDB);
            walletRepository.save(toWalletInstanceFromDB);
            String message = "Payment of " + transactionWalletRequest.getAmount() + "rs debited from " + transactionWalletRequest.getFromUsername() + " and credited to " + transactionWalletRequest.getToUsername() + " is done successfully.";
            transactionWalletStatus = TransactionWalletStatus.builder().status(Constant.SUCCESS).message(message).build();
        } catch (Exception e) {
            System.out.println("Exception "+e.getMessage());
            String message = "Payment of " + transactionWalletRequest.getAmount() + "rs debited from " + transactionWalletRequest.getFromUsername() + " and credited to " + transactionWalletRequest.getToUsername() + " is not done due to " + e.getMessage();
            transactionWalletStatus = TransactionWalletStatus.builder().status(Constant.FAILURE).message(message).build();
        }
        return transactionWalletStatus;
    }

    @Override
    public void deleteWallet(Long walletId) {
        Wallet walletInstanceFromDB = walletRepository.findById(walletId).orElseThrow(() -> new ResourceNotFoundException("Wallet is not found."));
        walletRepository.deleteById(walletInstanceFromDB.getId());
    }

    @Override
    public WalletResponse getWalletById(Long walletId) {
        Wallet walletInstanceFromDB = walletRepository.findById(walletId).orElseThrow(() -> new ResourceNotFoundException("Wallet is not found."));
        return this.mapWalletToWalletResponse(walletInstanceFromDB);
    }

    @Override
    public WalletResponse getWalletByUsername(String username) {
        Wallet walletInstanceFromDB = walletRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Wallet is not found with associated username."));
        return this.mapWalletToWalletResponse(walletInstanceFromDB);
    }

    private WalletResponse mapWalletToWalletResponse(Wallet wallet) {
        return WalletResponse.builder()
                .id(wallet.getId())
                .amount(wallet.getAmount())
                .username(wallet.getUsername())
                .active(wallet.getActive())
                .build();
    }

    private Wallet mapWalletRequestToWallet(WalletRequest walletRequest) {
        return Wallet.builder()
                .amount(walletRequest.getAmount())
                .username(walletRequest.getUsername())
                .active(walletRequest.getActive())
                .build();
    }
}
