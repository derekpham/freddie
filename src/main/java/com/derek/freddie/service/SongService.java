package com.derek.freddie.service;

import com.derek.freddie.entity.Genre;
import com.derek.freddie.entity.relationship.OfGenreRelationship;
import com.derek.freddie.entity.Song;
import com.derek.freddie.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public final class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Iterable<Song> findAll() {
        return songRepository.findAllOrderByArtist();
    }

    public Song save(Song song, Set<Genre> genres) {
        Set<OfGenreRelationship> relationships =
                genres
                        .stream()
                        .map(genre -> new OfGenreRelationship(song, genre))
                        .collect(Collectors.toSet());
        song.setGenreRelationships(relationships);
        this.songRepository.save(song);
        for (OfGenreRelationship relationship : relationships) {
            relationship.getGenre().getOfGenreRelationships().add(relationship);
        }
        return song;
    }

    public Song findByName(String name) {
        return this.songRepository.findByName(name);
    }
}
