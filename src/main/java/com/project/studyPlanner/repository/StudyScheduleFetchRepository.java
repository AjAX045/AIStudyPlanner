package com.project.studyPlanner.repository;

import com.project.studyPlanner.models.StudyPlan; // ✅ Correct class

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyScheduleFetchRepository extends JpaRepository<StudyPlan, Long> {
}
