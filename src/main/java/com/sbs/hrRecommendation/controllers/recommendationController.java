package com.sbs.hrRecommendation.controllers;
import com.sbs.hrRecommendation.models.recommendation;
import com.sbs.hrRecommendation.models.userProfile;
import com.sbs.hrRecommendation.repositories.userProfileRepository;
import com.sbs.hrRecommendation.repositories.recommendationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommendations")
public class recommendationController {

    @Autowired
    private recommendationRepository recRepository;
    @Autowired
    private userProfileRepository UserProfileRepository;

    //used to get all the recommendations present in db
    @GetMapping
    @RequestMapping("{id}")
    public Map<String, List<recommendation>> list(@PathVariable Long id) {
        List<recommendation> allRecomm = new ArrayList<recommendation>();
        List<recommendation> archived = new ArrayList<recommendation>();
        List<recommendation> myDrafts=new ArrayList<recommendation>();
        List<recommendation> myRecomm=new ArrayList<recommendation>();
        Map<String, List<recommendation>> map =new HashMap<String,List<recommendation>>();
        userProfile userdata = UserProfileRepository.getReferenceById(id);
        if(!UserProfileRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Id does not exist");
        if(userdata.getRoles().equals(userProfile.roles_enum.HR )){
            allRecomm = recRepository.findByIsArchivedAndMyStatusNot(false, recommendation.status.DRAFT);
            archived =recRepository.findByIsArchived(true);
            myDrafts =recRepository.findByUserIdAndMyStatus(id, recommendation.status.DRAFT);
            myRecomm =recRepository.findByUserIdAndIsArchivedAndMyStatusNot(id, false, recommendation.status.DRAFT);
            map.put("allRecommendations",allRecomm);
            map.put("archived",archived);
            map.put("myDrafts",myDrafts);
            map.put("myRecommendations",myRecomm);
        }
        else if (userdata.getRoles().equals(userProfile.roles_enum.USER)){
            allRecomm=recRepository.findByIsArchivedAndMyStatusNotAndIsPrivate(false,recommendation.status.DRAFT,false);
            myDrafts=recRepository.findByUserIdAndMyStatus(id, recommendation.status.DRAFT);
            myRecomm=recRepository.findByUserIdAndIsArchivedAndMyStatusNot(id, false, recommendation.status.DRAFT);
            map.put("allRecommendations",allRecomm);
            map.put("myDrafts",myDrafts);
            map.put("myRecommendations",myRecomm);
        }
        return map;
    }

    @PostMapping
    @RequestMapping("/save")
    public recommendation createDraft(@RequestBody final recommendation Recommendation) {
        Recommendation.setMyStatus(recommendation.status.DRAFT);
        return recRepository.saveAndFlush(Recommendation);

    }
    @PostMapping
    @RequestMapping("/publish")
    public recommendation createPublish(@RequestBody final recommendation Recommendation) {
        Recommendation.setMyStatus(recommendation.status.PENDING);
        return recRepository.saveAndFlush(Recommendation);

    }

    //delete a particular recommendation
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        recommendation rec = recRepository.getReferenceById(id);
        if(!recRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recommendation Id does not exist");
        if (!rec.getMyStatus().equals(recommendation.status.DRAFT))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status should be only draft");
        recRepository.deleteByRecommendationIdAndMyStatus(id, recommendation.status.DRAFT);
    }
}
