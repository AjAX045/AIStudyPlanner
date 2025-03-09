package com.project.studyPlanner.learningpod;

import org.springframework.stereotype.Service;
import com.project.studyPlanner.schedule.StudySchedule;
import java.util.ArrayList;
import java.util.List;

@Service
public class LearningPodService {

    private static final int MAX_POD_DURATION = 45; // Each pod should be at most 45 minutes

    public List<LearningPod> generateLearningPods(List<StudySchedule> studySchedule, int hoursPerDay) {
        List<LearningPod> learningPods = new ArrayList<>();

        for (StudySchedule dayPlan : studySchedule) {
            int totalMinutes = hoursPerDay * 60;
            int numberOfPods = (int) Math.ceil((double) totalMinutes / MAX_POD_DURATION); // Calculate pods dynamically
            int keyPointsPerPod = (int) Math.ceil((double) dayPlan.getKeyPoints().size() / numberOfPods);
            int resourcesPerPod = (int) Math.ceil((double) dayPlan.getResources().size() / numberOfPods);

            for (int i = 0; i < numberOfPods; i++) {
                int startKeyIndex = i * keyPointsPerPod;
                int endKeyIndex = Math.min(startKeyIndex + keyPointsPerPod, dayPlan.getKeyPoints().size());

                int startResIndex = i * resourcesPerPod;
                int endResIndex = Math.min(startResIndex + resourcesPerPod, dayPlan.getResources().size());

                // ✅ Extract Key Points and Resources **directly from AI-generated schedule**
                List<String> podKeyPoints = new ArrayList<>(dayPlan.getKeyPoints().subList(startKeyIndex, endKeyIndex));
                List<String> podResources = new ArrayList<>();

                // ✅ Convert StudyResource objects to Strings for LearningPods
                dayPlan.getResources().subList(startResIndex, endResIndex).forEach(resource -> 
                    podResources.add(resource.getTitle() + " - " + resource.getLink())
                );

                LearningPod pod = new LearningPod(
                        dayPlan.getDay(),
                        dayPlan.getTopic(),
                        i + 1, // Pod number for the day
                        "Focused Study on " + dayPlan.getTopic(),
                        MAX_POD_DURATION,
                        podKeyPoints,
                        podResources
                );

                learningPods.add(pod);
            }
        }
        return learningPods;
    }
}
