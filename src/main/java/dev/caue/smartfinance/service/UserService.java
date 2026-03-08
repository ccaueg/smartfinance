package dev.caue.smartfinance.service;

import dev.caue.smartfinance.domain.user.User;
import dev.caue.smartfinance.dto.LoginRequest;
import dev.caue.smartfinance.dto.RegisterRequest;
import dev.caue.smartfinance.exception.EmailAlreadyExistsException;
import dev.caue.smartfinance.exception.InvalidCredentialsException;
import dev.caue.smartfinance.mapper.UserMapper;
import dev.caue.smartfinance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email already in use");
        }

        String hashedPassword = passwordEncoder.encode(request.password());

        User user = userMapper.toEntity(request, hashedPassword);
        User savedUser = userRepository.save(user);

        log.info("Email {} registered successfully", savedUser.getEmail());

        return savedUser;
    }

    public User authenticate(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        log.info("Email {} authenticated successfully", user.getEmail());

        return user;
    }
}
