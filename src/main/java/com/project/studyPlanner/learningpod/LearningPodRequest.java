package com.project.studyPlanner.learningpod;

public class LearningPodRequest {
    private String subject;
    private int hoursPerDay;
    private int duration;
    private String learningStyle;
    private String difficulty;

    // ✅ Constructor
    public LearningPodRequest(String subject, int hoursPerDay, int duration, String learningStyle, String difficulty) {
        this.subject = subject;
        this.hoursPerDay = hoursPerDay;
        this.duration = duration;
        this.learningStyle = learningStyle;
        this.difficulty = difficulty;
    }

    // ✅ Getters and Setters
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getHoursPerDay() {
        return hoursPerDay;
    }

    public void setHoursPerDay(int hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLearningStyle() {
        return learningStyle;
    }

    public void setLearningStyle(String learningStyle) {
        this.learningStyle = learningStyle;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
