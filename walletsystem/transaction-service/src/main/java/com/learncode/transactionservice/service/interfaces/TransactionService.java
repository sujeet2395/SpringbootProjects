package com.learncode.transactionservice.service.interfaces;

import com.learncode.transactionservice.request.TransactionRequest;
import com.learncode.transactionservice.request.UpdateTransactionRequest;
import com.learncode.transactionservice.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest transactionRequest);
    
    TransactionResponse updateTransaction(String transactionId, UpdateTransactionRequest updateTransactionRequest);

    TransactionResponse getByTransactionId(String transactionId);
}
