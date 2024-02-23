package ru.zagrebin.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.zagrebin.auth.model.UserEntity;
import ru.zagrebin.auth.repository.AuthUserRepository;

@Component
@RequiredArgsConstructor
public class AuthUsersLoader implements CommandLineRunner {
    private final AuthUserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        repository.save(
                UserEntity.builder().userId(1)
                        .username("martin").password(passwordEncoder.encode("martin123"))
                        .build()
        );

        repository.save(
                UserEntity.builder().userId(2)
                        .username("bob").password(passwordEncoder.encode("bob123"))
                        .build()
        );
    }
}
