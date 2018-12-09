package com.derek.freddie.repository;

import com.derek.freddie.entity.Genre;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface GenreRepository extends Neo4jRepository<Genre, Long> {
    Optional<Genre> findByName(String name);
}
