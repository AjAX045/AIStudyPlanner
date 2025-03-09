package com.project.studyPlanner.schedule;

import com.project.studyPlanner.learningpod.LearningPodService;
import com.project.studyPlanner.learningpod.LearningPod;
import com.project.studyPlanner.schedule.StudySchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudyPlanService {

    @Autowired
    private LearningPodService learningPodService; // ✅ Injecting LearningPodService

    private static final int MAX_POD_DURATION = 45; // Max 45 min per pod

    public List<StudySchedule> generateStudyPlan(String subject, int hoursPerDay, int duration, String learningStyle, String difficulty) {
        List<StudySchedule> studySchedule = new ArrayList<>();

        for (int i = 1; i <= duration; i++) {
            StudySchedule dayPlan = new StudySchedule(
                    "Day " + i,
                    subject + " - Topic " + i,
                    hoursPerDay, null, null
            );

            studySchedule.add(dayPlan);
        }

        return studySchedule;
    }

    public List<LearningPod> generateLearningPodsFromSchedule(List<StudySchedule> studySchedule, int hoursPerDay) {
        return learningPodService.generateLearningPods(studySchedule, hoursPerDay);
    }
}
