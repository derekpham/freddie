package com.derek.freddie.controller;

import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import com.derek.freddie.entity.relationship.RelationshipType;
import com.derek.freddie.service.SongService;
import com.derek.freddie.service.UserActionService;
import com.derek.freddie.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/UserAction")
public final class UserActionController {
    private final UserService userService;
    private final SongService songService;
    private final UserActionService userActionService;

    public UserActionController(UserService userService, SongService songService, UserActionService userActionService) {
        this.userService = userService;
        this.songService = songService;
        this.userActionService = userActionService;
    }

    @PostMapping("/listened")
    @ResponseStatus(HttpStatus.OK)
    public boolean userListened(String userName, String songName) {
        User user = this.userService.findByName(userName);
        Song song = this.songService.findByName(songName);
        this.userActionService.registerUserAction(user, song, RelationshipType.LISTENED, null);
        return true;
    }

    @PostMapping("/liked")
    @ResponseStatus(HttpStatus.OK)
    public boolean userLikedOrDisliked(@RequestParam("userName") String userName,
                                       @RequestParam("songName") String songName,
                                       @RequestParam("liked") boolean liked) {
        User user = this.userService.findByName(userName);
        Song song = this.songService.findByName(songName);
        this.userActionService.registerUserAction(user, song,
                RelationshipType.GAVE_PREFERENCE, liked);
        return true;
    }
}
