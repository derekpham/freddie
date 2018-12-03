package com.derek.freddie.entity;

import com.derek.freddie.entity.relationship.GavePreference;
import com.derek.freddie.entity.relationship.ListenedRelationship;
import com.derek.freddie.entity.relationship.RelationshipType;
import com.derek.freddie.entity.relationship.WasRecommendedRelationship;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.*;

/**
 * TODO:
 *  +) looks into whether you can make this a set
 */

@NodeEntity
public final class User {
    @Id @GeneratedValue private Long id;
    private String name;
    private String algorithm;

    @Relationship(type = RelationshipType.WAS_RECOMMENDED)
    @JsonIgnore
    private Set<WasRecommendedRelationship> wasRecommendedRelationships = new HashSet<>();

    @Relationship(type = RelationshipType.LISTENED)
    @JsonIgnore
    private Set<ListenedRelationship> listenedRelationships = new HashSet<>();

    @Relationship(type = RelationshipType.GAVE_PREFERENCE)
    @JsonIgnore
    private Set<GavePreference> gavePreferenceRelationships = new HashSet<>();

    public User(String name, String algorithm) {
        this.name = name;
        this.algorithm = algorithm;
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

    public String getAlgorithm() {
        return algorithm;
    }

    public Set<WasRecommendedRelationship> getWasRecommendedRelationships() {
        return wasRecommendedRelationships;
    }

    public Set<ListenedRelationship> getListenedRelationships() {
        return listenedRelationships;
    }

    public Set<GavePreference> getGavePreferenceRelationships() {
        return gavePreferenceRelationships;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", algorithm='" + algorithm + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
