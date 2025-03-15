package com.project.studyPlanner.models;

import jakarta.persistence.*;
import java.util.List;

@Embeddable
public class LearningPod {
    private int podNumber;
    private String podTopic;
    private int durationMinutes;

    @ElementCollection
    private List<String> keyPoints;

    @ElementCollection
    private List<Resource> resources;

    public LearningPod() {}

    public LearningPod(int podNumber, String podTopic, int durationMinutes, List<String> keyPoints, List<Resource> resources) {
        this.podNumber = podNumber;
        this.podTopic = podTopic;
        this.durationMinutes = durationMinutes;
        this.keyPoints = keyPoints;
        this.resources = resources;
    }

    public int getPodNumber() { return podNumber; }
    public void setPodNumber(int podNumber) { this.podNumber = podNumber; }

    public String getPodTopic() { return podTopic; }
    public void setPodTopic(String podTopic) { this.podTopic = podTopic; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public List<String> getKeyPoints() { return keyPoints; }
    public void setKeyPoints(List<String> keyPoints) { this.keyPoints = keyPoints; }

    public List<Resource> getResources() { return resources; }
    public void setResources(List<Resource> resources) { this.resources = resources; }
}
