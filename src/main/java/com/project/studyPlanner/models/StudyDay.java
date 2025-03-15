package com.project.studyPlanner.models;

import jakarta.persistence.*;
import java.util.List;

@Embeddable
public class StudyDay {
    private String day;
    private String topic;
    private int hours;

    @ElementCollection
    private List<String> keyPoints;

    @ElementCollection
    private List<Resource> recommendedResources;

    @ElementCollection
    private List<LearningPod> learningPods;

    public StudyDay() {}

    public StudyDay(String day, String topic, int hours, List<String> keyPoints, List<Resource> recommendedResources, List<LearningPod> learningPods) {
        this.day = day;
        this.topic = topic;
        this.hours = hours;
        this.keyPoints = keyPoints;
        this.recommendedResources = recommendedResources;
        this.learningPods = learningPods;
    }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public int getHours() { return hours; }
    public void setHours(int hours) { this.hours = hours; }

    public List<String> getKeyPoints() { return keyPoints; }
    public void setKeyPoints(List<String> keyPoints) { this.keyPoints = keyPoints; }

    public List<Resource> getRecommendedResources() { return recommendedResources; }
    public void setRecommendedResources(List<Resource> recommendedResources) { this.recommendedResources = recommendedResources; }

    public List<LearningPod> getLearningPods() { return learningPods; }
    public void setLearningPods(List<LearningPod> learningPods) { this.learningPods = learningPods; }
}
