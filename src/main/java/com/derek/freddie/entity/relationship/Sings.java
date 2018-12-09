package com.derek.freddie.entity.relationship;

import com.derek.freddie.entity.Artist;
import com.derek.freddie.entity.Song;
import org.neo4j.ogm.annotation.*;

/**
 * Represents a directed SINGS relationship between an Artist node and a Song node.
 */
@RelationshipEntity(RelationshipType.SINGS)
public final class Sings {
    @Id @GeneratedValue private Long id;
    @StartNode private Artist artist;
    @EndNode private Song song;

    public Sings(Artist artist, Song song) {
        this.artist = artist;
        this.song = song;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
