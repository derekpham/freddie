package com.derek.freddie.repository;

import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

/**
 * TODO
 *   +) find solution for find allOrderBy without using a query?
 */

public interface SongRepository extends Neo4jRepository<Song, Long> {
    Song findByName(String name);

    /**
     * Recommends a random song. This can give you a song that you were already recommended to.
     */
    @Query("MATCH (s:Song) WITH s, rand() AS number RETURN s ORDER BY number LIMIT 1")
    Song findRandomSong(User user);

    /**
     * Recommends a random song that you have not listened to.
     */
    @Query("MATCH (u:User {name: {0}}), (s:Song) "
            + "WHERE NOT EXISTS ((u)-[:WAS_RECOMMENDED]->(s)) "
            + "WITH s, rand() as number "
            + "RETURN s "
            + "ORDER BY number "
            + "LIMIT 1")
    Optional<Song> findRandomSongSmart(String userName);

    /**
     * Recommends a song whose genre is as provided, and that the user hasn't listened, gave
     * reference to, or was recommended to
     */
    @Query("MATCH (s:Song)-[:OF_GENRE]->(g:Genre {name: {1}}) " +
            "MATCH (u:User {name: {0}}) " +
            "WHERE NOT EXISTS ((u)-[:LISTENED|:GAVE_PREFERENCE|:WAS_RECOMMENDED]->(s)) " +
            "WITH s, u, rand() as number " +
            "RETURN s " +
            "ORDER BY number " +
            "LIMIT 1")
    Optional<Song> findSongByGenre(String userName, String genreName);

    @Query("MATCH (u1:User {name: {0}})-[:GAVE_PREFERENCE {liked: true}]->(s:Song), " +
            "(u2:User {name: {1}}) " +
            "WHERE NOT (u2)-[:GAVE_PREFERENCE {liked: true}]->(s) " +
            "WITH s, rand() as number " +
            "RETURN s " +
            "ORDER BY number " +
            "LIMIT 1")
    Optional<Song> findSongFirstUserLikedButSecondHasNot(String userToExtract, String userToRecommend);

    @Query("MATCH (a:Artist {name: {0}})-[:SINGS]->(s:Song) " +
            "WITH s, rand() as number " +
            "RETURN s " +
            "ORDER BY number " +
            "LIMIT 1")
    Optional<Song> findRandomSongByArtist(String artistName);
}
