package com.derek.freddie.repository;

import com.derek.freddie.entity.Song;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SongRepository extends Neo4jRepository<Song, Long> {
}
