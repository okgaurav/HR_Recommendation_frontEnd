package com.sbs.hrRecommendation.repositories;

import com.sbs.hrRecommendation.models.voteKey;
import com.sbs.hrRecommendation.models.votes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface votesRepository extends JpaRepository<votes, voteKey> {

    Long CountByRecommendationId(Long id);

}
