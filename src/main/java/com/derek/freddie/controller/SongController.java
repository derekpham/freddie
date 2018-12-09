package com.derek.freddie.controller;

import com.derek.freddie.entity.Artist;
import com.derek.freddie.entity.Genre;
import com.derek.freddie.entity.Song;
import com.derek.freddie.service.ArtistService;
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
    private final ArtistService artistService;

    public SongController(SongService songService, GenreService genreService,
                          ArtistService artistService) {
        this.songService = songService;
        this.genreService = genreService;
        this.artistService = artistService;
    }

    @GetMapping("/")
    public Iterable<Song> findAll() {
        return this.songService.findAll();
    }

    @RequestMapping(value = "/addSong", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Song addSong(@RequestParam("name") String name, @RequestParam("artist") String artistName,
                        @RequestParam("year") int year, @RequestParam("url") String url,
                        @RequestParam("genre") String rawGenres) {
        Song song = new Song(name, year, url);
        Set<Genre> genres = this.genreService.saveGenresIfNotExist(rawGenres);
        Artist artist = this.artistService.saveArtistIfNotExists(artistName);
        this.songService.save(song, genres, artist);
        return song;
    }
}
