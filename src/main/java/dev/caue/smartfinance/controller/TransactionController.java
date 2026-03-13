package dev.caue.smartfinance.controller;

import dev.caue.smartfinance.domain.transaction.Transaction;
import dev.caue.smartfinance.dto.TransactionRequest;
import dev.caue.smartfinance.dto.TransactionResponse;
import dev.caue.smartfinance.mapper.TransactionMapper;
import dev.caue.smartfinance.security.UserDetailsImpl;
import dev.caue.smartfinance.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @Operation(summary = "Create transaction")
    @ApiResponse(responseCode = "201", description = "Transaction created")
    @PostMapping
    public TransactionResponse create(

            @Valid @RequestBody TransactionRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        Transaction transaction = transactionService.create(request, userDetails.getId());

        return transactionMapper.toResponse(transaction);
    }

    @Operation(summary = "List user transactions")
    @ApiResponse(responseCode = "200", description = "Transactions retrieved")
    @GetMapping
    public List<TransactionResponse> list(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return transactionService
                .findByUser(userDetails.getId())
                .stream()
                .map(transactionMapper::toResponse)
                .toList();
    }
}
