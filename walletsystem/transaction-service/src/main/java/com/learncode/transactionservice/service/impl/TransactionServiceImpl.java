package com.learncode.transactionservice.service.impl;

import com.learncode.transactionservice.apiconstant.TransactionStatus;
import com.learncode.transactionservice.entity.Transaction;
import com.learncode.transactionservice.exception.ResourceNotFoundException;
import com.learncode.transactionservice.repository.TransactionRepository;
import com.learncode.transactionservice.request.TransactionRequest;
import com.learncode.transactionservice.request.UpdateTransactionRequest;
import com.learncode.transactionservice.response.TransactionResponse;
import com.learncode.transactionservice.service.interfaces.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = this.mapTransactionRequestToTransaction(transactionRequest);
        //transaction.setTransactionStatus(TransactionStatus.PENDING);
        return this.mapTransactionToTransactionResponse(transactionRepository.save(transaction));
    }

    @Override
    public TransactionResponse updateTransaction(String transactionId, UpdateTransactionRequest updateTransactionRequest) {
        Transaction transactionInstanceFromDB = transactionRepository.findByTransactionId(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction is not found."));
        if (Objects.nonNull(transactionInstanceFromDB)) {
            if(transactionInstanceFromDB.getTransactionStatus().equals(TransactionStatus.NEW) && updateTransactionRequest.getTransactionStatus().equals(TransactionStatus.PENDING))
                transactionInstanceFromDB.setTransactionStatus(updateTransactionRequest.getTransactionStatus());
            else if(transactionInstanceFromDB.getTransactionStatus().equals(TransactionStatus.PENDING) && (updateTransactionRequest.getTransactionStatus().equals(TransactionStatus.SUCCESS) || updateTransactionRequest.getTransactionStatus().equals(TransactionStatus.FAILED) || updateTransactionRequest.getTransactionStatus().equals(TransactionStatus.CANCELLED)))
                transactionInstanceFromDB.setTransactionStatus(updateTransactionRequest.getTransactionStatus());
            else
                throw new IllegalStateException("Illegal Transaction Status");
            return this.mapTransactionToTransactionResponse(transactionRepository.save(transactionInstanceFromDB));
        }
        return null;
    }

    @Override
    public TransactionResponse getByTransactionId(String transactionId) {
        return this.mapTransactionToTransactionResponse(transactionRepository.findByTransactionId(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction is not found.")));
    }

    private TransactionResponse mapTransactionToTransactionResponse(Transaction transaction){
        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .fromUser(transaction.getFromUser())
                .toUser(transaction.getToUser())
                .amount(transaction.getAmount())
                .transactionStatus(transaction.getTransactionStatus())
                .build();
    }

    private Transaction mapTransactionRequestToTransaction(TransactionRequest transactionRequest){
        return Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .fromUser(transactionRequest.getFromUser())
                .toUser(transactionRequest.getToUser())
                .amount(transactionRequest.getAmount())
                .transactionStatus(TransactionStatus.NEW)
                .transactionType(transactionRequest.getTransactionType())
                .build();
    }
}
