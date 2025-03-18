package com.project.studyPlanner.repository;

import com.project.studyPlanner.models.StudyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudyPlanRepository extends JpaRepository<StudyPlan, Long> {
	
	Optional<StudyPlan> findBySubject(String subject); // ✅ Fetch study plan by subject name
	
	Optional<StudyPlan> findByUserId(String userId);

    Optional<StudyPlan> findById(Long id); // ✅ Keep this for backward compatibility

	
}
