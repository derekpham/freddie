package com.derek.freddie.controller;

import com.derek.freddie.entity.Song;
import com.derek.freddie.service.SongService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * TODO:
 *  +) change recommend from GET mapping to POST mapping
 *  +) move the algorithm method to a new controller?
 */

@RestController
@RequestMapping("/Song")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/")
    public Iterable<Song> findAll() {
        return this.songService.findAll();
    }

    @RequestMapping(value = "/addSong", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Song addSong(Song song) {
        return this.songService.save(song);
    }
}
