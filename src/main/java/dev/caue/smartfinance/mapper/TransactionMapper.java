package dev.caue.smartfinance.mapper;

import dev.caue.smartfinance.domain.transaction.Transaction;
import dev.caue.smartfinance.dto.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getCategory(),
                transaction.getType(),
                transaction.getDate()
        );
    }
}