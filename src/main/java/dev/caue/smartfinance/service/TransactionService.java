package dev.caue.smartfinance.service;

import dev.caue.smartfinance.domain.category.Category;
import dev.caue.smartfinance.domain.transaction.Transaction;
import dev.caue.smartfinance.domain.user.User;
import dev.caue.smartfinance.dto.TransactionRequest;
import dev.caue.smartfinance.exception.CategoryNotFoundException;
import dev.caue.smartfinance.exception.UserNotFoundException;
import dev.caue.smartfinance.repository.CategoryRepository;
import dev.caue.smartfinance.repository.TransactionRepository;
import dev.caue.smartfinance.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Transaction create(TransactionRequest request, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Transaction transaction = new Transaction();
        transaction.setDescription(request.description());
        transaction.setAmount(request.amount());
        transaction.setType(request.type());
        transaction.setDate(request.date());
        transaction.setUser(user);

        if (request.categoryId() != null) {
            Category category = categoryRepository
                    .findById(request.categoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

            if (!category.getUser().getId().equals(userId)) {
                throw new AccessDeniedException("Category does not belong to user");
            }

            transaction.setCategory(category);
        }

        return transactionRepository.save(transaction);
    }

    public List<Transaction> findByUser(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}
