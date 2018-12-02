package com.derek.freddie.service;

import com.derek.freddie.entity.Song;
import com.derek.freddie.repository.SongRepository;
import org.springframework.stereotype.Service;

@Service
public final class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Iterable<Song> findAll() {
        return songRepository.findAllOrderByArtist();
    }

    public Song save(Song song) {
        return this.songRepository.save(song);
    }
}
