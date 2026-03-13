package dev.caue.smartfinance.controller;

import dev.caue.smartfinance.domain.transaction.Transaction;
import dev.caue.smartfinance.dto.TransactionRequest;
import dev.caue.smartfinance.dto.TransactionResponse;
import dev.caue.smartfinance.mapper.TransactionMapper;
import dev.caue.smartfinance.security.UserDetailsImpl;
import dev.caue.smartfinance.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @PostMapping
    @Operation(summary = "Create transaction")
    public TransactionResponse create(

            @Valid @RequestBody TransactionRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        Transaction transaction = transactionService.create(request, userDetails.getId());

        return transactionMapper.toResponse(transaction);
    }
}
