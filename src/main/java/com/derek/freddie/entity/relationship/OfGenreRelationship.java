package com.derek.freddie.entity.relationship;

import com.derek.freddie.entity.Genre;
import com.derek.freddie.entity.Song;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.*;

/**
 * Represents a directed OF_GENRE relationship between a Song node and a Genre node.
 */
@RelationshipEntity(RelationshipType.OF_GENRE)
public final class OfGenreRelationship {
    @Id @GeneratedValue private Long id;
    @JsonIgnore @StartNode private Song song;
    @EndNode private Genre genre;

    public OfGenreRelationship(Song song, Genre genre) {
        this.song = song;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public Song getSong() {
        return song;
    }

    public Genre getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "OfGenreRelationship{" +
                "id=" + id +
                ", song=" + song +
                ", genre=" + genre +
                '}';
    }
}
