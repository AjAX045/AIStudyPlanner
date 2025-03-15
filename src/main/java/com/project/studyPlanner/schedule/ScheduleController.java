package com.project.studyPlanner.schedule;

import com.project.studyPlanner.learningpod.LearningPod;
import com.project.studyPlanner.learningpod.LearningPodService;
import com.project.studyPlanner.schedule.StudySchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/studyplan")
@CrossOrigin(origins = "*") // ✅ Enable CORS for frontend calls
public class ScheduleController {

    @Autowired
    private StudyPlanService studyPlanService;

    @Autowired
    private LearningPodService learningPodService;

    @PostMapping("/generate")
    public StudyPlanResponse generateStudyPlan(@RequestBody StudyPlanRequest request) {
        // ✅ Step 1: Generate Study Plan (AI-generated schedule)
        List<StudySchedule> studySchedule = studyPlanService.generateStudyPlan(
                request.getSubject(),
                request.getHoursPerDay(),
                request.getDuration(),
                request.getLearningStyle(),
                request.getDifficulty()
        );

        // ✅ Step 2: Automatically generate Learning Pods from Study Schedule
        List<LearningPod> learningPods = learningPodService.generateLearningPods(studySchedule, request.getHoursPerDay());

        // ✅ Step 3: Return both Study Plan and Learning Pods in a single response
        return new StudyPlanResponse(studySchedule, learningPods);
    }
}
