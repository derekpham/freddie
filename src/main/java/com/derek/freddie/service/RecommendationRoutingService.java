package com.derek.freddie.service;

import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import com.derek.freddie.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public final class RecommendationService {
    private final Map<String, Function<User, Song>> nameToAlgorithm;
    private final SongRepository songRepository;

    public RecommendationService(SongRepository songRepository) {
        this.songRepository = songRepository;
        this.nameToAlgorithm = new HashMap<>();
        nameToAlgorithm.put("random", songRepository::recommendRandomSong);
    }

    public List<String> allAlgorithms() {
        List<String> result = new ArrayList<>(this.nameToAlgorithm.keySet());
        Collections.sort(result);
        return result;
    }

    public boolean existsAlgorithm(String algorithm) {
        return algorithm != null && !this.nameToAlgorithm.containsKey(algorithm);
    }

    public Song recommend(User user) {
        if (this.existsAlgorithm(user.getAlgorithm())) {
            throw new RuntimeException("Algorithm doesn't exist");
        }

        return this.nameToAlgorithm.get(user.getAlgorithm()).apply(user);
    }
}