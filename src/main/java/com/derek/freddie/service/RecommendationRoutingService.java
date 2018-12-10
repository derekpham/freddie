package com.derek.freddie.service;

import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

/**
 * A dumb routing tier that routes algorithm names to recommendation implementations.
 */
@Service
public final class RecommendationRoutingService {
    private final Map<String, Function<User, Song>> nameToAlgorithm;
    private final RecommendationAlgorithmService recommendationAlgorithmService;

    public RecommendationRoutingService(RecommendationAlgorithmService recommendationAlgorithmService) {
        this.recommendationAlgorithmService = recommendationAlgorithmService;
        this.nameToAlgorithm = new HashMap<>();
        initMap();
    }

    private void initMap() {
        nameToAlgorithm.put("random", recommendationAlgorithmService::random);
        nameToAlgorithm.put("smartRandom", recommendationAlgorithmService::smartRandom);
        nameToAlgorithm.put("byGenre", recommendationAlgorithmService::byGenre);
        nameToAlgorithm.put("otherSimilarUsers",
                recommendationAlgorithmService::otherUsersThatLikedSameSongs);
        nameToAlgorithm.put("byArtist", recommendationAlgorithmService::byArtist);
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