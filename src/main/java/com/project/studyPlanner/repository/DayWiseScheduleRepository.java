package com.project.studyPlanner.repository;

import com.project.studyPlanner.models.DayWiseSchedule;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayWiseScheduleRepository extends JpaRepository<DayWiseSchedule, Long> {
	
    // ✅ Custom query method to find a schedule by userId
    
    Optional<DayWiseSchedule> findByUserIdAndSubject(String userId, String subject);
    
    // ❌ Fix: Change return type from Optional to List
    List<DayWiseSchedule> findByUserId(String userId);

}
