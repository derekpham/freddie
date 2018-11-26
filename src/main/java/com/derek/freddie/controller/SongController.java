package com.derek.freddie.controller;

import com.derek.freddie.entity.Song;
import com.derek.freddie.service.SongService;
import com.derek.freddie.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * TODO:
 *  +) change recommend from GET mapping to POST mapping
 */

@RestController
@RequestMapping("/Song")
public class SongController {
    private final SongService songService;
    private final UserService userService;

    public SongController(SongService songService, UserService userService) {
        this.songService = songService;
        this.userService = userService;
    }

    @GetMapping("/")
    public Iterable<Song> findAll() {
        return this.songService.findAll();
    }

    @GetMapping("/recommend")
    public Song recommend(@RequestParam("userName") String userName) {
        return this.songService.recommend(this.userService.findByName(userName));
    }

    @RequestMapping(value = "/addSong", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Song addSong(Song song) {
        return this.songService.save(song);
    }
}
