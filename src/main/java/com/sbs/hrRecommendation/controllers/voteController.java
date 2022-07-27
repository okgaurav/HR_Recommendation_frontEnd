package com.sbs.hrRecommendation.controllers;

import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.models.votes;
import com.sbs.hrRecommendation.repositories.userProfileRepository;
import com.sbs.hrRecommendation.repositories.votesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
public class voteController {

    @Autowired
    private votesRepository voteRepository;

    @GetMapping
    public Long get(@RequestParam(name ="recommendation_id")final Long recommendationId) {
        return voteRepository.CountByRecommendationId(recommendationId);
    }

    //push data into DB
    @PostMapping
    public votes create(@RequestBody final votes Votes) {
        return voteRepository.saveAndFlush(Votes);

        /*
            To Vote, It expects(Inside body):
            recommendation_id,
            user_id(who is voting),
            is_upvote
        */

    }
}
