package dev.caue.smartfinance.mapper;

import dev.caue.smartfinance.domain.user.UserRole;
import dev.caue.smartfinance.domain.user.User;
import dev.caue.smartfinance.dto.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(RegisterRequest request, String hashedPassword) {
        return User.builder()
                .name(request.name())
                .email(request.email())
                .password(hashedPassword)
                .role(UserRole.USER)
                .build();
    }
}
