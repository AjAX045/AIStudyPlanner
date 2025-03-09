package com.project.studyPlanner.calendar;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import com.project.studyPlanner.calendar.CalendarEvent;
import com.project.studyPlanner.calendar.CalendarEventRepository;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    private CalendarEventRepository calendarEventRepository;

    @PostMapping
    public ResponseEntity<CalendarEvent> createEvent(@RequestBody CalendarEvent event) {
        return ResponseEntity.ok(calendarEventRepository.save(event));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CalendarEvent>> getUserEvents(@PathVariable String userId) {
        return ResponseEntity.ok(calendarEventRepository.findByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        calendarEventRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

