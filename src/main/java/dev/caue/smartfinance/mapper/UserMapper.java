package dev.caue.smartfinance.mapper;

import dev.caue.smartfinance.domain.Role;
import dev.caue.smartfinance.domain.User;
import dev.caue.smartfinance.dto.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(RegisterRequest request, String hashedPassword) {
        return User.builder()
                .email(request.email())
                .password(hashedPassword)
                .role(Role.USER)
                .build();
    }
}
