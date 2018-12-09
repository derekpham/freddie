package com.derek.freddie.repository;

import com.derek.freddie.entity.Artist;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface ArtistRepository extends Neo4jRepository<Artist, Long> {
    Optional<Artist> findByName(String name);
}
