package com.derek.freddie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/UserAction")
public final class UserActionController {
    public UserActionController() { }

    @PostMapping("/listened")
    @ResponseStatus(HttpStatus.OK)
    public boolean userListened(String userName, String songName) {
        System.out.println(userName + " listened to " + songName);
        return true;
    }

    @PostMapping("/liked")
    @ResponseStatus(HttpStatus.OK)
    public boolean userLikedOrDisliked(@RequestParam("userName") String userName,
                                       @RequestParam("songName") String songName,
                                       @RequestParam("liked") boolean liked) {
        System.out.println(userName + (liked ? " liked " : " disliked ") + songName);
        return true;
    }
}
