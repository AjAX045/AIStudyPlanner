package com.project.studyPlanner.ai;



public class StudyPlan {
    private String subject;
    private String task;
    private int hoursRequired;

    public StudyPlan(String subject, String task, int hoursRequired) {
        this.subject = subject;
        this.task = task;
        this.hoursRequired = hoursRequired;
    }

    // Getters
    public String getSubject() { return subject; }
    public String getTask() { return task; }
    public int getHoursRequired() { return hoursRequired; }
}

