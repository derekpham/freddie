package com.derek.freddie.repository;

import com.derek.freddie.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface UserRepository extends Neo4jRepository<User, Long> {
}
