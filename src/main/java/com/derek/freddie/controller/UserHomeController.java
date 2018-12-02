package com.derek.freddie.controller;

import com.derek.freddie.entity.User;
import com.derek.freddie.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TODO:
 *  +) Handles error for mappings
 *  +) Handles user not found error
 *  +) Moves error to an error package??
 *  +) Even remove the exception class all in one
 */

@Controller
public final class UserHomeController {
    private UserService userService;

    public UserHomeController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/UserHome.html")
    public String userHome(@RequestParam("userName") String userName, Model model) {
        User user = userService.findByName(userName);
        if (user == null) {
            throw new UsernameNotFound(userName);
        }

        model.addAttribute("user", user);
        return "UserHome";
    }
}

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class UsernameNotFound extends RuntimeException {
    private String username;

    UsernameNotFound(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}