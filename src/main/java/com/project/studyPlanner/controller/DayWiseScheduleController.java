package com.project.studyPlanner.controller;

import com.project.studyPlanner.models.StudyPlan;
import com.project.studyPlanner.models.DayWiseSchedule;
import com.project.studyPlanner.service.DBServices;
import com.project.studyPlanner.service.DayWiseScheduleService;
import com.project.studyPlanner.ai.GeminiDayWiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/daywise")
public class DayWiseScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(DayWiseScheduleController.class);

    @Autowired
    private DBServices dbServices;

    @Autowired
    private DayWiseScheduleService dayWiseScheduleService;

    @Autowired
    private GeminiDayWiseService geminiDayWiseService;

    /**
     * ✅ Generates a day-wise study schedule for a subject.
     */
    @PostMapping("/generate")
    public Map<String, Object> generateDayWiseSchedule(@RequestBody Map<String, String> requestData) {
        String subject = requestData.get("subject");
        String userId = requestData.get("userId");

        logger.info("Received schedule generation request for subject='{}', userId='{}'", subject, userId);

        // 🛠 Validate Input
        if (subject == null || subject.isEmpty() || userId == null || userId.isEmpty()) {
            return Map.of("error", "Missing required parameters: subject and/or userId");
        }

        // 🛠 Check if the study plan exists
        Optional<StudyPlan> studyPlanOptional = dbServices.getStudyPlanBySubject(subject);
        if (studyPlanOptional.isEmpty()) {
            logger.warn("Study plan not found for subject '{}'", subject);
            return Map.of("error", "Study plan not found for subject: " + subject);
        }
        StudyPlan studyPlan = studyPlanOptional.get();

        // 🛠 Check if the schedule already exists
        Optional<DayWiseSchedule> existingSchedule = dayWiseScheduleService.findByUserIdAndSubject(userId, subject);
        if (existingSchedule.isPresent()) {
            logger.info("Schedule already exists for subject '{}', user '{}'", subject, userId);
            return Map.of("message", "Schedule already generated. Fetching today's schedule.");
        }

        // 🛠 Generate the day-wise schedule via Gemini AI
        DayWiseSchedule newSchedule = geminiDayWiseService.generateAndStoreDayWiseSchedule(subject, userId);

        if (newSchedule != null) {
            logger.info("Successfully generated schedule for subject '{}', user '{}'", subject, userId);
            return Map.of("message", "Schedule successfully generated!", "schedule", newSchedule);
        } else {
            logger.error("Failed to generate schedule for subject '{}', user '{}'", subject, userId);
            return Map.of("error", "Failed to generate schedule.");
        }
    }

    /**
     * ✅ Fetches today's schedule based on the start date.
     */
    @GetMapping("/today")
    public Map<String, Object> getTodaySchedule(@RequestParam String userId) {
        logger.info("Fetching today's schedule for user '{}'", userId);

        // 🛠 Validate userId
        if (userId == null || userId.trim().isEmpty()) {
            logger.warn("Invalid request: Missing userId");
            return Map.of("error", "Missing required parameter: userId");
        }

        LocalDate today = LocalDate.now();
        Map<String, Object> response = dayWiseScheduleService.getTodaySchedule(userId, today);

        if (response.containsKey("message")) {
            logger.warn("No schedule found for today for user '{}'", userId);
        } else {
            logger.info("Successfully fetched today's schedule for user '{}'", userId);
        }

        return response;
    }
}
