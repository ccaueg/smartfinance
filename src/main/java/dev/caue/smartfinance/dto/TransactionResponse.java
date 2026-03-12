package dev.caue.smartfinance.dto;

import dev.caue.smartfinance.domain.category.Category;
import dev.caue.smartfinance.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(
        Long id,
        String description,
        BigDecimal amount,
        Category category,
        TransactionType type,
        LocalDate date
) {}