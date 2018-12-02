package com.derek.freddie.controller;

import com.derek.freddie.entity.Genre;
import com.derek.freddie.entity.Song;
import com.derek.freddie.service.GenreService;
import com.derek.freddie.service.SongService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * TODO:
 *  +) change recommend from GET mapping to POST mapping
 *  +) move the algorithm method to a new controller?
 */

@RestController
@RequestMapping("/Song")
public class SongController {
    private final SongService songService;
    private final GenreService genreService;

    public SongController(SongService songService, GenreService genreService) {
        this.songService = songService;
        this.genreService = genreService;
    }

    @GetMapping("/")
    public Iterable<Song> findAll() {
        return this.songService.findAll();
    }

    @RequestMapping(value = "/addSong", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Song addSong(@RequestParam("name") String name, @RequestParam("artist") String artist,
                        @RequestParam("year") int year, @RequestParam("url") String url,
                        @RequestParam("genre") String rawGenres) {
        Song song = new Song(name, artist, year, url);
        Set<Genre> genres = this.genreService.saveGenres(rawGenres);
        this.songService.save(song, genres);
        this.genreService.saveAll(genres);
        return song;
    }
}
