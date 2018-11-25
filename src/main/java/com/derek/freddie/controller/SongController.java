package com.derek.freddie.controller;

import com.derek.freddie.entity.Song;
import com.derek.freddie.service.SongService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
