package com.learncode.walletservice.controller;

import com.learncode.walletservice.request.AddMoneyIntoWalletRequest;
import com.learncode.walletservice.request.TransactionWalletRequest;
import com.learncode.walletservice.request.UpdateWalletRequest;
import com.learncode.walletservice.request.WalletRequest;
import com.learncode.walletservice.service.interfaces.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    // Create a new wallet
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createWallet(@RequestBody WalletRequest walletRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", walletService.createWallet(walletRequest));
            response.put("success", true);
        } catch (Exception e) {
            response.put("exception", e.getMessage());
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update an existing wallet
    @PutMapping("/{walletId}")
    public ResponseEntity<Map<String, Object>> updateWallet(@PathVariable Long walletId, @RequestBody UpdateWalletRequest updateWalletRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", walletService.updateWallet(walletId, updateWalletRequest));
            response.put("success", true);
        } catch (Exception e) {
            response.put("exception", e.getMessage());
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // add money into an existing wallet
    @PutMapping("/{walletId}/add-money")
    public ResponseEntity<Map<String, Object>> addMoneyIntoWallet(@PathVariable Long walletId, @RequestBody AddMoneyIntoWalletRequest addMoneyIntoWalletRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", walletService.addMoneyIntoWallet(walletId, addMoneyIntoWalletRequest));
            response.put("success", true);
        } catch (Exception e) {
            response.put("exception", e.getMessage());
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete a wallet by ID
    @DeleteMapping("/{walletId}")
    public ResponseEntity<Map<String, Object>> deleteWallet(@PathVariable Long walletId) {
        Map<String, Object> response = new HashMap<>();
        try {
            walletService.deleteWallet(walletId);
            response.put("data", "Wallet is deleted successfully.");
            response.put("success", true);
        } catch (Exception e) {
            response.put("exception", e.getMessage());
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get a wallet by ID
    @GetMapping("/{walletId}")
    public ResponseEntity<Map<String, Object>> getWalletById(@PathVariable Long walletId) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", walletService.getWalletById(walletId));
            response.put("success", true);
        } catch (Exception e) {
            response.put("exception", e.getMessage());
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get a wallet by username
    @GetMapping("/by-username/{username}")
    public ResponseEntity<Map<String, Object>> getWalletByUsername(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", walletService.getWalletByUsername(username));
            response.put("success", true);
        } catch (Exception e) {
            response.put("exception", e.getMessage());
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // transaction wallet
    @PostMapping("/transaction")
    public ResponseEntity<Map<String, Object>> transactionWallet(@RequestBody TransactionWalletRequest transactionWalletRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", walletService.transactionWallet(transactionWalletRequest));
            response.put("success", true);
        } catch (Exception e) {
            response.put("exception", e.getMessage());
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
