package com.derek.freddie.service;

import com.derek.freddie.entity.Artist;
import com.derek.freddie.entity.Genre;
import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.relationship.OfGenreRelationship;
import com.derek.freddie.entity.relationship.Sings;
import com.derek.freddie.repository.SongRepository;
import org.neo4j.ogm.session.Session;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public final class SongService {
    private final SongRepository songRepository;
    private Session session;

    public SongService(SongRepository songRepository, Session session) {
        this.songRepository = songRepository;
        this.session = session;
    }

    public Iterable<Song> findAll() {
        Iterable<Song> result = this.songRepository.findAll();
        result.forEach(song -> {
            session.load(Song.class, song.getId(), 2);
        });
        return result;
    }

    public Song save(Song song, Set<Genre> genres, Artist artist) {
        Set<OfGenreRelationship> relationships =
                genres
                        .stream()
                        .map(genre -> new OfGenreRelationship(song, genre))
                        .collect(Collectors.toSet());
        song.setGenreRelationships(relationships);

        Sings singsRelationShip = new Sings(artist, song);
        song.setArtistRelationship(singsRelationShip);
        artist.getSongRelationships().add(singsRelationShip);

        this.songRepository.save(song);
        return song;
    }

    public Song findByName(String name) {
        return this.songRepository.findByName(name);
    }
}
