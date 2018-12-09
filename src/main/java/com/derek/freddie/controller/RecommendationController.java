package com.derek.freddie.controller;

import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import com.derek.freddie.entity.relationship.RelationshipType;
import com.derek.freddie.service.RecommendationRoutingService;
import com.derek.freddie.service.UserActionService;
import com.derek.freddie.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles anything that is related to recommending songs.
 * Right now, it handles returning all available algorithms, and recommends you the next song.
 * API endpoint at /Recommend
 */
@RestController
@RequestMapping("Recommend")
public final class RecommendationController {
    private final RecommendationRoutingService recommendationRoutingService;
    private final UserService userService;
    private final UserActionService userActionService;

    public RecommendationController(RecommendationRoutingService recommendationRoutingService,
                                    UserService userService,
                                    UserActionService userActionService) {
        this.recommendationRoutingService = recommendationRoutingService;
        this.userService = userService;
        this.userActionService = userActionService;
    }

    @GetMapping("/algorithms")
    public List<String> allAlgorithms() {
        return this.recommendationRoutingService.allAlgorithms();
    }

    /**
     * Recommends you a song! This will also create a relationship in the database between the
     * recommended song and the user ("WAS_RECOMMENDED" relationship).
     * @param userName the user to recommend
     * @return the recommended song
     */
    @PostMapping("/recommend")
    public Song recommend(@RequestParam("userName") String userName) {
        User user = this.userService.findByName(userName);
        Song song = this.recommendationRoutingService.recommend(user);
        this.userActionService.registerUserAction(user, song,
                RelationshipType.WAS_RECOMMENDED, null);
        return song;
    }
}
