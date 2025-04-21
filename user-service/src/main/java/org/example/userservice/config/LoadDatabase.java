package org.example.userservice.config;

import org.example.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            if (repository.count() == 0) { // chỉ thêm khi chưa có user
//                repository.save(new User(null, "user1", "password1", "user1@example.com", "password1", "password1", "Admin"));
//                repository.save(new User(null, "user2", "password2", "user2@example.com", "password2", "password2", "Admin"));
//                System.out.println("✅ Inserted initial users.");
            }
        };
    }
}
