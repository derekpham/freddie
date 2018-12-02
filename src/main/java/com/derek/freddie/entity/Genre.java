package com.derek.freddie.entity;

import com.derek.freddie.entity.relationship.OfGenreRelationship;
import com.derek.freddie.entity.relationship.RelationshipType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity
public final class Genre {
    @Id @GeneratedValue private Long id;
    private String name;

    @Relationship(type = RelationshipType.OF_GENRE, direction = Relationship.INCOMING)
    @JsonIgnore
    private Set<OfGenreRelationship> ofGenreRelationships;

    public Genre(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<OfGenreRelationship> getOfGenreRelationships() {
        return ofGenreRelationships;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
