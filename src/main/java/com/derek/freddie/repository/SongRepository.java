package com.derek.freddie.repository;

import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * TODO
 *   +) find solution for find all order by without using a query?
 */

public interface SongRepository extends Neo4jRepository<Song, Long> {
    Song findByName(String name);

    @Query("MATCH (s:Song) WITH s, rand() AS number RETURN s ORDER BY number LIMIT 1")
    Song recommendRandomSong(User user);

    @Query("MATCH (s:Song) RETURN s ORDER BY s.artist")
    Iterable<Song> findAllOrderByArtist();
}
