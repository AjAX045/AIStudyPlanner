package com.project.studyPlanner.schedule;

import com.project.studyPlanner.learningpod.LearningPod;
import com.project.studyPlanner.schedule.StudySchedule;
import java.util.List;

public class StudyPlanResponse {
    private List<StudySchedule> studySchedule;
    private List<LearningPod> learningPods;

    public StudyPlanResponse(List<StudySchedule> studySchedule, List<LearningPod> learningPods) {
        this.studySchedule = studySchedule;
        this.learningPods = learningPods;
    }

    public List<StudySchedule> getStudySchedule() {
        return studySchedule;
    }

    public List<LearningPod> getLearningPods() {
        return learningPods;
    }
}
