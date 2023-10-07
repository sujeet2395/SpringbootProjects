package com.learncode.transactionservice.controller;

import com.learncode.transactionservice.exception.ResourceNotFoundException;
import com.learncode.transactionservice.request.TransactionRequest;
import com.learncode.transactionservice.request.UpdateTransactionRequest;
import com.learncode.transactionservice.response.TransactionResponse;
import com.learncode.transactionservice.service.interfaces.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Object>> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("Transaction initiated: {}", transactionRequest);
            response.put("data", transactionService.createTransaction(transactionRequest));
            response.put("success", true);
        } catch (Exception e) {
            log.info("Exception Occurred :- " + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Map<String, Object>> updateTransaction(@PathVariable String transactionId, @RequestBody UpdateTransactionRequest updateTransactionRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("Transaction update initiated: {}", updateTransactionRequest);
            TransactionResponse transactionResponse = transactionService.updateTransaction(transactionId, updateTransactionRequest);
            if(Objects.isNull(transactionResponse))
                throw new ResourceNotFoundException("Transaction is not found");
            response.put("data",transactionResponse);
            response.put("success",true);
        } catch (Exception e) {
            log.info("Exception Occurred :- " + e.getMessage());
            response.put("exception", e.getMessage());
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Map<String, Object>> getTransactionByTransactionId(@PathVariable String transactionId) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data",transactionService.getByTransactionId(transactionId));
            response.put("success",true);
        } catch (Exception e) {
            response.put("exception", e.getMessage());
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
