package com.derek.freddie.entity.relationship;

import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import org.neo4j.ogm.annotation.*;

import java.util.Objects;

/**
 * Represents a GAVE_PREFERENCE relationship in the database.
 * Directed relationship between a User node and a Song node.
 * Has a boolean attribute "liked" to represent whether the user has liked or disliked the song.
 */
@RelationshipEntity(RelationshipType.GAVE_PREFERENCE)
public final class GavePreference {
    @Id @GeneratedValue private Long id;
    @StartNode private User user;
    @EndNode private Song song;
    private boolean liked;

    public GavePreference(User user, Song song, boolean liked) {
        this.user = user;
        this.song = song;
        this.liked = liked;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Song getSong() {
        return song;
    }

    public boolean isLiked() {
        return liked;
    }

    @Override
    public String toString() {
        return "GavePreference{" +
                "id=" + id +
                ", user=" + user.getName() +
                ", song=" + song.getName() +
                ", liked=" + liked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GavePreference that = (GavePreference) o;
        return liked == that.liked &&
                Objects.equals(user, that.user) &&
                Objects.equals(song, that.song);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, song, liked);
    }
}
