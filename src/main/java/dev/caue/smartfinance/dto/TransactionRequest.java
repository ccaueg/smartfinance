package dev.caue.smartfinance.dto;

import dev.caue.smartfinance.domain.category.Category;
import dev.caue.smartfinance.domain.transaction.TransactionType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(
        String description,

        @NotNull
        @Positive
        BigDecimal amount,

        Category category,

        @NotNull
        TransactionType type,

        @NotNull
        LocalDate date
) {}