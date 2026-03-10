package dev.caue.smartfinance.service;

import dev.caue.smartfinance.domain.user.UserRole;
import dev.caue.smartfinance.domain.user.User;
import dev.caue.smartfinance.dto.LoginRequest;
import dev.caue.smartfinance.dto.RegisterRequest;
import dev.caue.smartfinance.exception.EmailAlreadyExistsException;
import dev.caue.smartfinance.exception.InvalidCredentialsException;
import dev.caue.smartfinance.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void register_WhenEmailAlreadyExists_ShouldThrowException() {
        RegisterRequest request = new RegisterRequest("Test", "test@email.com", "123456");

        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.register(request));
    }

    @Test
    void authenticate_WhenPasswordIsInvalid_ShouldThrowException() {
        LoginRequest request = new LoginRequest("test@email.com", "wrong");

        User user = User.builder()
                .id(1L)
                .email("test@email.com")
                .password("encoded")
                .userRole(UserRole.USER)
                .build();

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> userService.authenticate(request));
    }

    @Test
    void authenticate_WhenCredentialsAreValid_ShouldReturnUser() {
        LoginRequest request = new LoginRequest("test@email.com", "123456");

        User user = User.builder()
                .id(1L)
                .email("test@email.com")
                .password("encoded")
                .userRole(UserRole.USER)
                .build();

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "encoded")).thenReturn(true);

        User result = userService.authenticate(request);

        assertEquals(user.getEmail(), result.getEmail());
    }
}