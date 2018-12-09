package com.derek.freddie.service;

import com.derek.freddie.entity.Artist;
import com.derek.freddie.repository.ArtistRepository;
import org.springframework.stereotype.Service;

@Service
public final class ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Artist saveArtistIfNotExists(String artistName) {
        return this.artistRepository
                .findByName(artistName)
                .orElseGet(() -> this.artistRepository.save(new Artist(artistName)));
    }

    public Artist save(Artist artist) {
        return this.artistRepository.save(artist);
    }
}
