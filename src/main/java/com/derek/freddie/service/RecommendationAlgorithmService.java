package com.derek.freddie.service;

import com.derek.freddie.entity.Genre;
import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import com.derek.freddie.entity.relationship.GavePreference;
import com.derek.freddie.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public final class RecommendationAlgorithmService {
    private final SongRepository songRepository;

    public RecommendationAlgorithmService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    /**
     * Returns a random song
     */
    public Song random(User user) {
        return this.songRepository.recommendRandomSong(user);
    }

    /**
     * Returns a random song that hasn't been recommended to the user
     * TODO: take into account disliked preferences
     */
    public Song smartRandom(User user) {
        Optional<Song> song = this.songRepository.smartRecommendRandomSong(user.getName());
        return song.orElseGet(() -> this.random(user));
    }

    /**
     * Returns a song that the user hasn't heard, based on the genres that he/she has liked
     */
    public Song byGenre(User user) {
        List<Genre> likedGenres = extractLikedGenres(user.getGavePreferenceRelationships());
        if (likedGenres.isEmpty()) {
            return this.smartRandom(user);
        }

        Genre randomGenre = listRandom(likedGenres);
        return this.songRepository
                .byGenre(user.getName(), randomGenre.getName())
                .orElseGet(() -> this.smartRandom(user));
    }

    private List<Genre> extractLikedGenres(Set<GavePreference> preferences) {
        List<Genre> result = new ArrayList<>();

        preferences
                .stream()
                .filter(GavePreference::isLiked)
                .map(GavePreference::getSong)
                .map(Song::extractGenres)
                .forEach(result::addAll);

        return result;
    }

    private <T> T listRandom(List<T> elements) {
        return elements.get(new Random().nextInt(elements.size()));
    }
}
