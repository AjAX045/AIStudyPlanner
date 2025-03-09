package com.project.studyPlanner.progress;



import jakarta.persistence.*;

@Entity
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String topic;
    private int totalHoursStudied;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public int getTotalHoursStudied() { return totalHoursStudied; }
    public void setTotalHoursStudied(int totalHoursStudied) { this.totalHoursStudied = totalHoursStudied; }
}

