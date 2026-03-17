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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Operation(summary = "Create transaction")
    @ApiResponse(responseCode = "201", description = "Transaction created")
    @ResponseStatus(HttpStatus.CREATED)
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

    @Operation(summary = "Get transaction by ID")
    @ApiResponse(responseCode = "200", description = "Transaction retrieved")
    @GetMapping("/{id}")
    public TransactionResponse getById(

            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        Transaction transaction = transactionService.findById(id, userDetails.getId());

        return transactionMapper.toResponse(transaction);
    }

    @Operation(summary = "Delete transaction")
    @ApiResponse(responseCode = "204", description = "Transaction deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(

            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        transactionService.delete(id, userDetails.getId());

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update transaction")
    @ApiResponse(responseCode = "200", description = "Transaction updated")
    @PutMapping("/{id}")
    public TransactionResponse update(

            @PathVariable Long id,
            @Valid @RequestBody TransactionRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        Transaction transaction = transactionService.update(id, request, userDetails.getId());

        return transactionMapper.toResponse(transaction);
    }
}
