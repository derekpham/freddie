package com.derek.freddie.entity;

import com.derek.freddie.entity.relationship.OfGenreRelationship;
import com.derek.freddie.entity.relationship.RelationshipType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@NodeEntity
public final class Song {
    @Id @GeneratedValue private Long id;
    private String name;
    private String artist;
    private int year;
    private String url;

    @Relationship(type = RelationshipType.OF_GENRE)
    @JsonIgnore
    private Set<OfGenreRelationship> genreRelationships = new HashSet<>();

    public Song() {}

    public Song(String name, String artist, int yearPublished, String url) {
        this.name = name;
        this.artist = artist;
        this.year = yearPublished;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    public String getUrl() {
        return url;
    }

    public Set<OfGenreRelationship> getGenreRelationships() {
        return genreRelationships;
    }

    public void setGenreRelationships(Set<OfGenreRelationship> genreRelationships) {
        this.genreRelationships = genreRelationships;
    }

    public Set<Genre> extractGenres() {
        return genreRelationships
                .stream()
                .map(OfGenreRelationship::getGenre)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", year=" + year +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(id, song.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
