package com.lktech.surveytalks.repository;

import com.lktech.surveytalks.model.Like;
import com.lktech.surveytalks.model.QuestionLikeCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRespository extends JpaRepository<Like, Long> {

    @Query("SELECT NEW com.lktech.surveytalks.model.QuestionLikeCount(v.like.id, count(v.id)) FROM Like v WHERE v.survey.id in :surveyIds GROUP BY v.like.id")
    List<QuestionLikeCount> countBySurveyIdInGroupByLikeId(@Param("surveyIds") List<Long> surveyIds);

    @Query("SELECT NEW com.lktech.surveytalks.model.QuestionLikeCount(v.like.id, count(v.id)) FROM Like v WHERE v.survey.id = :surveyId GROUP BY v.like.id")
    List<QuestionLikeCount> countBySurveyIdGroupByLikeId(@Param("surveyId") Long surveyIds);

    @Query("SELECT v FROM Like v where v.user.id = :userId and v.survey.id in :surveyIds")
    List<Like> findByUserIdAndSurveyIdIn(@Param("userId") Long userId, @Param("surveyId") List<Long> surveyIds);

    @Query("SELECT v FROM Like v where v.user.id = :userId and v.survey.id = :surveyId")
    Like findByUserIdAndSurveyId(@Param("userId") Long userId, @Param("surveyId") Long surveyIds);

    @Query("SELECT COUNT(v.id) from Like v where v.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT v.poll.id FROM Like v WHERE v.user.id = :userId")
    Page<Long> findLikedSurveyIdsByUserId(@Param("userId") Long userId, Pageable pageable);

}
