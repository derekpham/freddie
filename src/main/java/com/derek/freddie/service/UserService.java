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
    private final RecommendationService recommendationService;

    public UserService(UserRepository userRepository, RecommendationService recommendationService) {
        this.userRepository = userRepository;
        this.recommendationService = recommendationService;
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User findByName(String userName) {
        return this.userRepository.findByName(userName);
    }

    public User save(User user) {
        if (this.existsByName(user.getName())) {
            throw new RuntimeException("Username already exists");
        }
        if (!this.recommendationService.algorithmExists(user.getAlgorithm())) {
            throw new RuntimeException(("Unknown algorithm"));
        }

        return this.userRepository.save(user);
    }

    private boolean existsByName(String name) {
        return this.userRepository.findByName(name) != null;
    }
}
