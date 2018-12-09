package com.derek.freddie.service;

import com.derek.freddie.entity.Genre;
import com.derek.freddie.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public final class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Set<Genre> saveGenresIfNotExist(String[] genres) {
        Set<Genre> result = new HashSet<>();

        Arrays.stream(genres)
                .forEach(genreName -> {
                    result.add(this.genreRepository
                            .findByName(genreName)
                            .orElseGet(() -> this.genreRepository.save(new Genre(genreName))));
                });

        return result;
    }
}
