package com.project.studyPlanner.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Entity
@Table(name = "study_plans")
public class StudyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // ✅ New field to store the user ID

    private String subject;
    private int hoursPerDay;
    private int duration;
    private String learningStyle;
    private String difficulty;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String studyData; 

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt; 

    public StudyPlan() {}

    public StudyPlan(String userId, String subject, int hoursPerDay, int duration, String learningStyle, String difficulty, String studyData) {
        this.userId = userId;
        this.subject = subject;
        this.hoursPerDay = hoursPerDay;
        this.duration = duration;
        this.learningStyle = learningStyle;
        this.difficulty = difficulty;
        this.studyData = studyData;
        this.createdAt = LocalDateTime.now();
    }

    // ✅ Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; } // ✅ New getter/setter for userId

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public int getHoursPerDay() { return hoursPerDay; }
    public void setHoursPerDay(int hoursPerDay) { this.hoursPerDay = hoursPerDay; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getLearningStyle() { return learningStyle; }
    public void setLearningStyle(String learningStyle) { this.learningStyle = learningStyle; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getStudyData() { return studyData; }
    public void setStudyData(String studyData) { this.studyData = studyData; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
