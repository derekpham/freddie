package com.derek.freddie.controller;

import com.derek.freddie.entity.Song;
import com.derek.freddie.service.RecommendationService;
import com.derek.freddie.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Recommend")
public final class RecommendationController {
    private final RecommendationService recommendationService;
    private final UserService userService;

    public RecommendationController(RecommendationService recommendationService,
                                    UserService userService) {
        this.recommendationService = recommendationService;
        this.userService = userService;
    }

    @GetMapping("/algorithms")
    public List<String> allAlgorithms() {
        return this.recommendationService.allAlgorithms();
    }

    @PostMapping("/recommend")
    public Song recommend(@RequestParam("userName") String userName) {
        return this.recommendationService.recommend(this.userService.findByName(userName));
    }
}
