package com.derek.freddie.service;

import com.derek.freddie.entity.Artist;
import com.derek.freddie.entity.Genre;
import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import com.derek.freddie.entity.relationship.GavePreference;
import com.derek.freddie.entity.relationship.Sings;
import com.derek.freddie.repository.SongRepository;
import com.derek.freddie.repository.UserRepository;
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
    private final UserRepository userRepository;

    public RecommendationAlgorithmService(SongRepository songRepository, Session session,
                                          UserRepository userRepository) {
        this.songRepository = songRepository;
        this.session = session;
        this.userRepository = userRepository;
    }

    /**
     * Returns a random song.
     */
    public Song random(User user) {
        return this.songRepository.findRandomSong(user);
    }

    /**
     * Returns a random song that hasn't been recommended to the user.
     * TODO: take into account disliked preferences
     */
    public Song smartRandom(User user) {
        Optional<Song> song = this.songRepository.findRandomSongSmart(user.getName());
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
                .findSongByGenre(user.getName(), randomGenre.getName())
                .orElseGet(() -> this.smartRandom(user));
    }

    /**
     * This algorithm will fetch all the songs that you have liked, look for users with similar
     * interests, and then recommend songs by the best ranked user.
     * This is kind of inefficient since we are looking at all users, but I couldn't find a
     * built-in tool in Neo4j to do something like this.
     */
    public Song otherUsersThatLikedSameSongs(User user) {
        Set<Song> likedSongs = extractLikedSongs(user);
        if (likedSongs.isEmpty()) {
            return this.smartRandom(user);
        }

        SortedMap<Integer, List<User>> leaderBoard = new TreeMap<>();
        this.userRepository.findAll().forEach(otherUser -> {
            if (!user.getId().equals(otherUser.getId())) {
                int score = countCommonElements(likedSongs, extractLikedSongs(otherUser));
                if (!leaderBoard.containsKey(score)) {
                    leaderBoard.put(score, new ArrayList<>());
                }
                leaderBoard.get(score).add(otherUser);
            }
        });

        List<User> rankedUsers = flattenLeaderBoard(leaderBoard);
        for (User rankedUser : rankedUsers) {
            Optional<Song> recommendedSong = this.songRepository.findSongFirstUserLikedButSecondHasNot
                    (rankedUser.getName(), user.getName());
            if (recommendedSong.isPresent()) {
                return recommendedSong.get();
            }
        }

        return this.smartRandom(user);
    }

    /**
     * Recommends a random song by a randomly chosen artist that this user has liked.
     */
    public Song byArtist(User user) {
        List<Artist> likedArtists = new ArrayList<>(extractLikedArtists(user));
        if (likedArtists.isEmpty()) {
            return this.smartRandom(user);
        }

        Artist randomLikeArtist = listRandom(likedArtists);
        return this.songRepository.findRandomSongByArtist(randomLikeArtist.getName())
                .orElseGet(() -> this.smartRandom(user));
    }

    private List<User> flattenLeaderBoard(SortedMap<Integer, List<User>> leaderBoard) {
        List<User> result = new ArrayList<>();

        leaderBoard
                .keySet()
                .forEach(score -> result.addAll(leaderBoard.get(score)));

        return result;
    }

    private Set<Song> extractLikedSongs(User user) {
        return user
                .getGavePreferenceRelationships()
                .stream()
                .filter(GavePreference::isLiked)
                .map(GavePreference::getSong)
                .collect(Collectors.toSet());
    }

    private Set<Artist> extractLikedArtists(User user) {
        return this.extractLikedSongs(user)
                .stream()
                .map(song -> this.session.load(Song.class, song.getId(), 2))
                .map(Song::getArtistRelationship)
                .map(Sings::getArtist)
                .collect(Collectors.toSet());
    }

    private int countCommonElements(Set<Song> set1, Set<Song> set2) {
        int result = 0;

        for (Song song: set1) {
            if (set2.contains(song)) {
                result++;
            }
        }

        return result;
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
