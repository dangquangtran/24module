package org.example.userservice.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    private final HttpServletRequest request;
    @Override
    public Optional<String> getCurrentAuditor() {
        String username = request.getHeader("X-Username");

        if (username != null && !username.isEmpty()) {
            return Optional.of(username);
        }

        return Optional.of("anonymous");
    }
}
