package com.derek.freddie.entity.relationship;

import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import org.neo4j.ogm.annotation.*;

import java.util.Objects;

@RelationshipEntity(RelationshipType.GAVE_PREFERENCE)
public final class GavePreferenceRelationship {
    @Id @GeneratedValue private Long id;
    @StartNode private User user;
    @EndNode private Song song;
    private boolean liked;

    public GavePreferenceRelationship(User user, Song song, boolean liked) {
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
        return "GavePreferenceRelationship{" +
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
        GavePreferenceRelationship that = (GavePreferenceRelationship) o;
        return liked == that.liked &&
                Objects.equals(user, that.user) &&
                Objects.equals(song, that.song);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, song, liked);
    }
}
