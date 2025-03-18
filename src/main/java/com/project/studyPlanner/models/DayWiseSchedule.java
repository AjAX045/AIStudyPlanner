package com.project.studyPlanner.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "day_wise_schedule")
public class DayWiseSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // ✅ Storing the user ID
    private String subject; // ✅ Storing the subject

    @Lob
    @Column(columnDefinition = "TEXT")
    private String dayWiseData;

    private LocalDate startDate; // ✅ Added startDate field

    public DayWiseSchedule() {}

    // ✅ Constructor
    public DayWiseSchedule(String userId, String subject, String dayWiseData, LocalDate startDate) {
        this.userId = userId;
        this.subject = subject;
        this.dayWiseData = dayWiseData;
        this.startDate = startDate;
    }

    // ✅ Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDayWiseData() { return dayWiseData; }
    public void setDayWiseData(String dayWiseData) { this.dayWiseData = dayWiseData; }

    public LocalDate getStartDate() { return startDate; } // ✅ Getter for startDate
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; } // ✅ Setter for startDate
}
