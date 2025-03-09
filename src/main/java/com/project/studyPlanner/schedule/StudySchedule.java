package com.project.studyPlanner.schedule;

import java.util.List;

public class StudySchedule {
    private String day;
    private String topic;
    private int hours;
    private List<String> keyPoints;
    private List<StudyResource> resources;

    // Constructor
    public StudySchedule(String day, String topic, int hours, List<String> keyPoints, List<StudyResource> resources) {
        this.day = day;
        this.topic = topic;
        this.hours = hours;
        this.keyPoints = keyPoints;
        this.resources = resources;
    }

    // Getters and Setters
    public String getDay() { return day; }
    public String getTopic() { return topic; }
    public int getHours() { return hours; }
    public List<String> getKeyPoints() { return keyPoints; }
    public List<StudyResource> getResources() { return resources; }

    public void setResources(List<StudyResource> resources) { this.resources = resources; }
}
