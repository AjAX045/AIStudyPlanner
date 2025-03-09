package com.project.studyPlanner.learningpod;

import com.project.studyPlanner.schedule.StudySchedule;
import com.project.studyPlanner.schedule.StudyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learningpods")
@CrossOrigin(origins = "*") // ✅ Allow frontend to access this API
public class LearningPodController {

    @Autowired
    private LearningPodService learningPodService;

    @Autowired
    private StudyPlanService studyPlanService; // ✅ Fetch AI-generated study schedule

    @PostMapping("/generate")
    public List<LearningPod> generateLearningPods(@RequestBody LearningPodRequest request) {
        // ✅ Fetch the AI-generated study schedule for the user
        List<StudySchedule> studySchedule = studyPlanService.generateStudyPlan(
                request.getSubject(),
                request.getHoursPerDay(),
                request.getDuration(),
                request.getLearningStyle(),
                request.getDifficulty()
        );

        // ✅ Generate Learning Pods from the AI-generated schedule
        return learningPodService.generateLearningPods(studySchedule, request.getHoursPerDay());
    }
}
