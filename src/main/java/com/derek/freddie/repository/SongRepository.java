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
    Song recommendRandomSong(User user);

    /**
     * Recommends a random song that you have not listened to.
     */
    @Query("MATCH (u:User {name: {0}}), (s:Song) "
            + "WHERE NOT EXISTS ((u)-[:WAS_RECOMMENDED]->(s)) "
            + "WITH s, rand() as number "
            + "RETURN s "
            + "ORDER BY number "
            + "LIMIT 1")
    Optional<Song> smartRecommendRandomSong(String userName);

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
    Optional<Song> byGenre(String userName, String genreName);

    @Query("MATCH (u:User)-[:GAVE_PREFERENCE {liked: true}]->(s:Song {name: {1}}) " +
            "MATCH (u:User)-[:GAVE_PREFERENCE {liked: true}]->(song:Song) " +
            "WHERE song.name <> {1} " +
            "AND u.name <> {0} " +
            "WITH song, rand() as number " +
            "RETURN song " +
            "ORDER BY number " +
            "LIMIT 1")
    Optional<Song> byWhoAlsoLikedThisSong(String userName, String songName);
}
