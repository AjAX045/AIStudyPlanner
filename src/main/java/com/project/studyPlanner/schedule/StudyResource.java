package com.project.studyPlanner.schedule;

public class StudyResource {
    private String type;
    private String title;
    private String link;

    // Constructor
    public StudyResource(String type, String title, String link) {
        this.type = type;
        this.title = title;
        this.link = link;
    }

    // Getters
    public String getType() { return type; }
    public String getTitle() { return title; }
    public String getLink() { return link; }
}
