package com.learncode.transactionservice.request;

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
public class UpdateTransactionRequest {

    private TransactionStatus transactionStatus;
}
