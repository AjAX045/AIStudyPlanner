package com.project.studyPlanner.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.studyPlanner.calendar.CalendarEvent;
import java.util.List;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    List<CalendarEvent> findByUserId(String userId);
}
