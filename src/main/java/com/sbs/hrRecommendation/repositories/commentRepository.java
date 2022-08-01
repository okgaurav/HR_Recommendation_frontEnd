package com.sbs.hrRecommendation.repositories;

import com.sbs.hrRecommendation.dto.CommentResponse;
import com.sbs.hrRecommendation.models.comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface commentRepository extends JpaRepository<comments, Long> { //We created this interface because  we get operations like find, update, delete.

    @Query
            ("SELECT new com.sbs.hrRecommendation.dto.CommentResponse(c.recommendationId,c.userId,c.commentId,c.commentDescription," +
            "  c.commentType,c.createdAt, u.userName, u.employeeId, u.designation, u.roles)" +
            " FROM comments c, users u where c.userId=u.userId and c.recommendationId = ?1")
    List<CommentResponse> findAllComments(Long id);

    @Modifying
    @Query
            ("UPDATE recommendations r set r.count = r.count + 1 WHERE r.recommendationId = ?1")

    Integer countByRecommendationId(Long id);

}
