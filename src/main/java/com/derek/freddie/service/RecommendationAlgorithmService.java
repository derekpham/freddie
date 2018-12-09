package com.derek.freddie.service;

import com.derek.freddie.entity.Genre;
import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import com.derek.freddie.entity.relationship.GavePreference;
import com.derek.freddie.repository.SongRepository;
import org.neo4j.ogm.session.Session;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This service contains all methods that implement different recommendation algorithms. All the
 * recommendation methods have the same signature: takes in an User, and returns a Song.
 */
@Service
public final class RecommendationAlgorithmService {
    private final SongRepository songRepository;
    private final Session session;

    public RecommendationAlgorithmService(SongRepository songRepository, Session session) {
        this.songRepository = songRepository;
        this.session = session;
    }

    /**
     * Returns a random song.
     */
    public Song random(User user) {
        return this.songRepository.recommendRandomSong(user);
    }

    /**
     * Returns a random song that hasn't been recommended to the user.
     * TODO: take into account disliked preferences
     */
    public Song smartRandom(User user) {
        Optional<Song> song = this.songRepository.smartRecommendRandomSong(user.getName());
        return song.orElseGet(() -> this.random(user));
    }

    /**
     * Returns a song that the user hasn't heard, based on the genres that he/she has liked.
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

    /**
     * Finds users that have liked the same songs that this user has liked, and returns one song
     * that those users have also liked.
     * TODO: Takes into account user's liked/disliked history
     */
    public Song otherUserThatLikedSameSongs(User user) {
        List<Song> likedSong = user
                .getGavePreferenceRelationships()
                .stream()
                .filter(GavePreference::isLiked)
                .map(GavePreference::getSong)
                .collect(Collectors.toList());
        if (likedSong.isEmpty()) {
            return this.smartRandom(user);
        }

        Song randomLikedSong = listRandom(likedSong);
        return this.songRepository
                .byWhoAlsoLikedThisSong(user.getName(), randomLikedSong.getName())
                .orElseGet(() -> this.smartRandom(user));
    }

    private List<Genre> extractLikedGenres(Set<GavePreference> preferences) {
        List<Genre> result = new ArrayList<>();

        preferences
                .stream()
                .filter(GavePreference::isLiked)
                .map(GavePreference::getSong)
                .map(song -> session.load(Song.class, song.getId(), 2))
                .map(Song::extractGenres)
                .forEach(result::addAll);

        return result;
    }

    private <T> T listRandom(List<T> elements) {
        return elements.get(new Random().nextInt(elements.size()));
    }
}
