package com.sbs.hrRecommendation.controllers;


import com.sbs.hrRecommendation.dto.CommentResponse;
import com.sbs.hrRecommendation.models.comments;
import com.sbs.hrRecommendation.models.recommendation;
import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.repositories.commentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class commentController {

    @Autowired
    private commentRepository comRepository;

    @PostMapping
    @RequestMapping()
    public comments createComment(@RequestBody final comments Comment) {
        return comRepository.saveAndFlush(Comment);
        /*
            To Post Comment, It expects(Inside body):
            recommendation_id,comment_description,
            user_id(who is commenting),comment_type
        */
    }

    @GetMapping
    @RequestMapping("/count/{id}")
    public Integer getCount(@PathVariable Long id)
    {
            return comRepository.countByRecommendationId(id);
            //Count no. of comments for a recommendation id
    }

    @GetMapping
    @RequestMapping("{id}")
    public List<CommentResponse> get(@PathVariable Long id){
        return comRepository.findAllComments(id);
        /*
            Here "id" passed in path params=RecommendationId
        */
    }
}
