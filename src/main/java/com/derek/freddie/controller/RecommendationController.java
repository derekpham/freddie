package com.derek.freddie.controller;

import com.derek.freddie.entity.Song;
import com.derek.freddie.entity.User;
import com.derek.freddie.entity.relationship.RelationshipType;
import com.derek.freddie.service.RecommendationService;
import com.derek.freddie.service.UserActionService;
import com.derek.freddie.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Recommend")
public final class RecommendationController {
    private final RecommendationService recommendationService;
    private final UserService userService;
    private final UserActionService userActionService;

    public RecommendationController(RecommendationService recommendationService,
                                    UserService userService,
                                    UserActionService userActionService) {
        this.recommendationService = recommendationService;
        this.userService = userService;
        this.userActionService = userActionService;
    }

    @GetMapping("/algorithms")
    public List<String> allAlgorithms() {
        return this.recommendationService.allAlgorithms();
    }

    @PostMapping("/recommend")
    public Song recommend(@RequestParam("userName") String userName) {
        User user = this.userService.findByName(userName);
        Song song = this.recommendationService.recommend(user);
        this.userActionService.registerUserAction(user, song,
                RelationshipType.WAS_RECOMMENDED, null);
        return song;
    }
}
