package com.project.studyPlanner.repository;

import com.project.studyPlanner.models.QuizScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizScoreRepository extends JpaRepository<QuizScore, Long> {
    List<QuizScore> findByUserId(String userId);
    List<QuizScore> findByUserIdAndSubject(String userId, String subject);
}