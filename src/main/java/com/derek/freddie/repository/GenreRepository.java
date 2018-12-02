package com.derek.freddie.repository;

import com.derek.freddie.entity.Genre;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface GenreRepository extends Neo4jRepository<Genre, Long> {
    Genre findByName(String name);
}
