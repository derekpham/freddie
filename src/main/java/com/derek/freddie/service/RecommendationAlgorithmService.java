package com.derek.freddie.service;

import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import com.derek.freddie.repository.SongRepository;
import org.springframework.stereotype.Service;

@Service
public final class RecommendationAlgorithmService {
    private final SongRepository songRepository;

    public RecommendationAlgorithmService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song random(User user) {
        return this.songRepository.recommendRandomSong(user);
    }

    public Song smartRandom(User user) {
        return this.songRepository.smartRecommendRandomSong(user.getName());
    }
}
