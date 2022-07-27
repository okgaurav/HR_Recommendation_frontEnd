package com.sbs.hrRecommendation.repositories;

import com.sbs.hrRecommendation.models.voteKey;
import com.sbs.hrRecommendation.models.votes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface votesRepository extends JpaRepository<votes, voteKey> {

    @Query
            ("SELECT count(v.isUpvote) FROM recommendationvotes v where v.votekey.recommendationId = ?1 and v.isUpvote=true")
    Long CountUpvote(Long id);

    @Query
            ("SELECT count(v.isUpvote) FROM recommendationvotes v where v.votekey.recommendationId = ?1 and v.isUpvote=false")
    Long CountDownvote(Long id);
}
