package com.derek.freddie.service;

import com.derek.freddie.entity.Genre;
import com.derek.freddie.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public final class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Set<Genre> saveGenresIfNotExist(String rawGenres) {
        Set<Genre> result = new HashSet<>();

        for (String genreName : rawGenres.split(";(\\s*)")) {
            Genre genre = this.genreRepository.findByName(genreName.toLowerCase());
            if (genre == null) {
                genre = new Genre(genreName);
                this.genreRepository.save(genre);
            }

            result.add(genre);
        }

        return result;
    }

    public Iterable<Genre> saveAll(Set<Genre> genres) {
        return this.genreRepository.saveAll(genres);
    }
}
