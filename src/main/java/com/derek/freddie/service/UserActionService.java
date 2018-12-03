package com.derek.freddie.service;

import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import com.derek.freddie.entity.relationship.GavePreference;
import com.derek.freddie.entity.relationship.ListenedRelationship;
import com.derek.freddie.entity.relationship.RelationshipType;
import com.derek.freddie.entity.relationship.WasRecommendedRelationship;
import com.derek.freddie.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public final class UserActionService {
    private final UserRepository userRepository;

    public UserActionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUserAction(User user, Song song, String userAction, Boolean liked) {
        if (!RelationshipType.isUserSongRelationship(userAction)) {
            throw new IllegalArgumentException("not a user-song relationship: " + userAction);
        }

        if (userAction.equals(RelationshipType.WAS_RECOMMENDED)) {
            user.getWasRecommendedRelationships().add(new WasRecommendedRelationship(user, song));
        } else if (userAction.equals(RelationshipType.LISTENED)) {
            user.getListenedRelationships().add(new ListenedRelationship(user, song));
        } else if (userAction.equals(RelationshipType.GAVE_PREFERENCE) && liked != null) {
            Set<GavePreference> likedDislikedRelationships = user
                    .getGavePreferenceRelationships();
            likedDislikedRelationships.remove(new GavePreference(user, song, !liked));
            likedDislikedRelationships.add(new GavePreference(user, song, liked));
        } else {
            throw new IllegalArgumentException("must give a boolean for preference");
        }

        this.userRepository.save(user);
    }
}
