package dev.caue.smartfinance.service;

import dev.caue.smartfinance.domain.transaction.Transaction;
import dev.caue.smartfinance.exception.TransactionNotFoundException;
import dev.caue.smartfinance.repository.CategoryRepository;
import dev.caue.smartfinance.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void findById_WhenTransactionExists_ShouldReturnTransaction() {
        Long userId = 1L;
        Long transactionId = 1L;

        Transaction transaction = new Transaction();
        transaction.setId(transactionId);

        when(transactionRepository.findByIdAndUserId(transactionId, userId))
                .thenReturn(Optional.of(transaction));

        Transaction result = transactionService.findById(transactionId, userId);

        assertEquals(transactionId, result.getId());
    }

    @Test
    void findById_WhenTransactionDoesNotExist_ShouldThrowException() {
        Long userId = 1L;
        Long transactionId = 1L;

        when(transactionRepository.findByIdAndUserId(transactionId, userId))
                .thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class,
                () -> transactionService.findById(transactionId, userId));
    }

    @Test
    void delete_WhenTransactionExists_ShouldDelete() {
        Long userId = 1L;
        Long transactionId = 1L;

        Transaction transaction = new Transaction();
        transaction.setId(transactionId);

        when(transactionRepository.findByIdAndUserId(transactionId, userId))
                .thenReturn(Optional.of(transaction));

        transactionService.delete(transactionId, userId);

        verify(transactionRepository).delete(transaction);
    }
}
