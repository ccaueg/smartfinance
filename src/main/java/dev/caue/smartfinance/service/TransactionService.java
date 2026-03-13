package dev.caue.smartfinance.service;

import dev.caue.smartfinance.domain.transaction.Transaction;
import dev.caue.smartfinance.domain.user.User;
import dev.caue.smartfinance.dto.TransactionRequest;
import dev.caue.smartfinance.exception.UserNotFoundException;
import dev.caue.smartfinance.repository.TransactionRepository;
import dev.caue.smartfinance.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Transaction create(TransactionRequest request, Long userId) {
        Transaction transaction = new Transaction();

        transaction.setDescription(request.description());
        transaction.setAmount(request.amount());
        transaction.setCategory(request.category());
        transaction.setType(request.type());
        transaction.setDate(request.date());

        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found"));

        transaction.setUser(user);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> findByUser(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}
