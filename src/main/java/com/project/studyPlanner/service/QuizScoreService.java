package com.project.studyPlanner.service;

import com.project.studyPlanner.models.QuizScore;
import com.project.studyPlanner.repository.QuizScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizScoreService {
    @Autowired
    private QuizScoreRepository quizScoreRepository;

    public QuizScore saveQuizScore(QuizScore quizScore) {
        return quizScoreRepository.save(quizScore);
    }

    public List<QuizScore> getScoresByUser(String userId) {
        return quizScoreRepository.findByUserId(userId);
    }

    public List<QuizScore> getScoresByUserAndSubject(String userId, String subject) {
        return quizScoreRepository.findByUserIdAndSubject(userId, subject);
    }
}
