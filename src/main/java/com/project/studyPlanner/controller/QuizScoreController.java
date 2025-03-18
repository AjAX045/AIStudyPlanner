package com.project.studyPlanner.controller;

import com.project.studyPlanner.models.QuizScore;
import com.project.studyPlanner.service.QuizScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-score")
public class QuizScoreController {
    @Autowired
    private QuizScoreService quizScoreService;

    @PostMapping("/save")
    public ResponseEntity<QuizScore> saveQuizScore(@RequestBody QuizScore quizScore) {
        return ResponseEntity.ok(quizScoreService.saveQuizScore(quizScore));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QuizScore>> getScoresByUser(@PathVariable String userId) {
        return ResponseEntity.ok(quizScoreService.getScoresByUser(userId));
    }

    @GetMapping("/user/{userId}/subject/{subject}")
    public ResponseEntity<List<QuizScore>> getScoresByUserAndSubject(@PathVariable String userId, @PathVariable String subject) {
        return ResponseEntity.ok(quizScoreService.getScoresByUserAndSubject(userId, subject));
    }
}