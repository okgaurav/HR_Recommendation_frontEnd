package com.sbs.hrRecommendation.repositories;

import com.sbs.hrRecommendation.models.voteKey;
import com.sbs.hrRecommendation.models.votes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface votesRepository extends JpaRepository<votes, voteKey> {

    @Query
            ("SELECT count(v.isUpvote) FROM recommendationvotes v where v.votekey.recommendationId = ?1 and v.isUpvote=true")
    Long CountUpvote(Long id);

    @Query
            ("SELECT count(v.isUpvote) FROM recommendationvotes v where v.votekey.recommendationId = ?1 and v.isUpvote=false")
    Long CountDownvote(Long id);

    @Query
            ("SELECT v.isUpvote FROM recommendationvotes v where v.votekey.recommendationId = ?2 and v.votekey.userId = ?1")
    boolean getLikesData(Long user_id, Long recomm_id);

    @Modifying
    @Query
            ("UPDATE recommendations r set r.countUpvote = r.countUpvote + 1 WHERE r.recommendationId = ?1")
    Integer countUpvoteByRecommendationId(Long id);

    @Modifying
    @Query
            ("UPDATE recommendations r set r.countUpvote = r.countUpvote - 1 WHERE r.recommendationId = ?1 and r.countUpvote <> 0")
    Integer countUpvoteMinusByRecommendationId(Long id);

    @Modifying
    @Query
            ("UPDATE recommendations r set r.countDownvote = r.countDownvote + 1 WHERE r.recommendationId = ?1")
    Integer countDownvoteByRecommendationId(Long id);

    @Modifying
    @Query
            ("UPDATE recommendations r set r.countDownvote = r.countDownvote - 1 WHERE r.recommendationId = ?1 and r.countDownvote <> 0")
    Integer countDownvoteMinusByRecommendationId(Long id);

}