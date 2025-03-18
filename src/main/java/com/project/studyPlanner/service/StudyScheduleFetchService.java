package com.project.studyPlanner.service;

import com.project.studyPlanner.models.StudyPlan; // ✅ Correct class

import com.project.studyPlanner.repository.StudyScheduleFetchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudyScheduleFetchService {

    @Autowired
    private StudyScheduleFetchRepository studyScheduleFetchRepository;

    public StudyPlan getRawScheduleById(Long scheduleId) {
        Optional<StudyPlan> schedule = studyScheduleFetchRepository.findById(scheduleId);
        return schedule.orElse(null);
    }
}
