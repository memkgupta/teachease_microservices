package org.teachease.authorisationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teachease.authorisationservice.entities.User;
import org.teachease.authorisationservice.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User createUser(String userId) {
        User user = new User();
        user.setUserId(userId);
        return userRepository.save(user);
    }
    public User getOrCreateUser(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return user.orElse(null);
    }
}
