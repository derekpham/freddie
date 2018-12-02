package com.derek.freddie.entity.relationship;

import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import org.neo4j.ogm.annotation.*;

import java.util.Objects;

@RelationshipEntity(RelationshipType.LISTENED)
public final class ListenedRelationship {
    @Id @GeneratedValue private Long id;
    @StartNode private User user;
    @EndNode private Song song;

    public ListenedRelationship(User user, Song song) {
        this.user = user;
        this.song = song;
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

    @Override
    public String toString() {
        return "ListenedRelationship{" +
                "id=" + id +
                ", user=" + user.getName() +
                ", song=" + song.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListenedRelationship that = (ListenedRelationship) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(song, that.song);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, song);
    }
}
