package com.sbs.hrRecommendation.controllers;

import com.sbs.hrRecommendation.dto.RecommendationResponse;
import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.models.votes;
import com.sbs.hrRecommendation.repositories.userProfileRepository;
import com.sbs.hrRecommendation.repositories.votesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/votes")
public class voteController {

    @Autowired
    private votesRepository voteRepository;

    @GetMapping
    @RequestMapping("{id}")
    public Map<String,Long> get(@PathVariable Long id) {
        Long up,down;
        up=voteRepository.CountUpvote(id);
        down=voteRepository.CountDownvote(id);
        Map<String, Long> map =new HashMap<String,Long>();
        map.put("upVote",up);
        map.put("downVote",down);
        return  map;
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
