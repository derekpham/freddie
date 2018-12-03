package com.derek.freddie.service;

import com.derek.freddie.entity.User;
import com.derek.freddie.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * TODO:
 * +) Figure out why existsByName in UserRepository is returning a null instead of boolean
 * +) Custom exceptions, maybe pass to controllers???
 */

@Service
public final class UserService {
    private final UserRepository userRepository;
    private final RecommendationRoutingService recommendationRoutingService;

    public UserService(UserRepository userRepository, RecommendationRoutingService recommendationRoutingService) {
        this.userRepository = userRepository;
        this.recommendationRoutingService = recommendationRoutingService;
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User findByName(String userName) {
        return this.userRepository.findByName(userName);
    }

    public User save(User user) {
        if (this.userInDatabase(user)) {
            throw new RuntimeException("User already exists: " + user);
        }
        if (this.recommendationRoutingService.existsAlgorithm(user.getAlgorithm())) {
            throw new RuntimeException(("Unknown algorithm"));
        }

        return this.userRepository.save(user);
    }

    private boolean userInDatabase(User user) {
        return user.getId() == null && this.existsByName(user.getName());
    }

    private boolean existsByName(String name) {
        return this.userRepository.findByName(name) != null;
    }
}
