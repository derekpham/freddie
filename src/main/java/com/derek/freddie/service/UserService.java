package com.derek.freddie.service;

import com.derek.freddie.entity.User;
import com.derek.freddie.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public final class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Iterable<User> findAll() {
        return repository.findAll();
    }
}
