package com.derek.freddie.service;

import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import com.derek.freddie.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public final class SongService {
    private final SongRepository songRepository;
    private final Map<String, Function<User, Song>> recommendationMapper;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
        this.recommendationMapper = new HashMap<>();
        this.recommendationMapper.put("random", songRepository::recommendRandomSong);
    }

    public Iterable<Song> findAll() {
        return songRepository.findAllOrderByArtist();
    }

    public Song recommend(User user) {
        return this.recommendationMapper.get(user.getAlgorithm()).apply(user);
    }

    public Song save(Song song) {
        return this.songRepository.save(song);
    }
}
