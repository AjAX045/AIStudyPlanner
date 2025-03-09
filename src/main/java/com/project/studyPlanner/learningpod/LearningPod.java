package com.project.studyPlanner.learningpod;

import jakarta.persistence.*;
import java.util.List;

@Entity  // ✅ Marking this as a JPA entity
@Table(name = "learning_pods") // ✅ Table name in the database
public class LearningPod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ✅ Auto-generate unique ID
    private Long id;

    private String day;
    private String topic;
    private int podNumber;
    private String description;
    private int durationMinutes;

    @ElementCollection  // ✅ Needed to store a list in JPA
    private List<String> keyPoints;

    @ElementCollection  // ✅ Needed to store a list in JPA
    private List<String> resources;

    // ✅ Default Constructor (Required by JPA)
    public LearningPod() {}

    // ✅ Constructor
    public LearningPod(String day, String topic, int podNumber, String description, int durationMinutes, 
                       List<String> keyPoints, List<String> resources) {
        this.day = day;
        this.topic = topic;
        this.podNumber = podNumber;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.keyPoints = keyPoints;
        this.resources = resources;
    }

    // ✅ Getters & Setters (Required by JPA)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public int getPodNumber() { return podNumber; }
    public void setPodNumber(int podNumber) { this.podNumber = podNumber; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public List<String> getKeyPoints() { return keyPoints; }
    public void setKeyPoints(List<String> keyPoints) { this.keyPoints = keyPoints; }

    public List<String> getResources() { return resources; }
    public void setResources(List<String> resources) { this.resources = resources; }
}
