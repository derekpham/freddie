package com.derek.freddie.entity;

import com.derek.freddie.entity.relationship.RelationshipType;
import com.derek.freddie.entity.relationship.Sings;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public final class Artist {
    @Id @GeneratedValue private Long id;
    private String name;

    @Relationship(type = RelationshipType.SINGS)
    @JsonIgnore
    private Set<Sings> songRelationships = new HashSet<>();

    public Artist(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Sings> getSongRelationships() {
        return songRelationships;
    }

    public void setSongRelationships(Set<Sings> songRelationships) {
        this.songRelationships = songRelationships;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
