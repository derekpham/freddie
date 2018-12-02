package com.derek.freddie.controller;

import com.derek.freddie.entity.User;
import com.derek.freddie.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public final class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public Iterable<User> findAll() {
        return this.userService.findAll();
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User addUser(User user) {
        return this.userService.save(user);
    }
}
