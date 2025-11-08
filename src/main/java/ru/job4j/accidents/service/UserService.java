package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserRepository;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class.getName());

    public Optional<User> save(User user) {
        Optional<User> result = Optional.empty();
        try {
            User savedUser = userRepository.save(user);
            result = Optional.of(savedUser);
        } catch (Exception ex) {
            LOG.error("Error saving user '{}': {}", user.getUsername(), ex.getMessage());
        }
        return result;
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
