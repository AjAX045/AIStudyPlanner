package com.project.studyPlanner.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.studyPlanner.models.DayWiseSchedule;
import com.project.studyPlanner.repository.DayWiseScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DayWiseScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(DayWiseScheduleService.class);

    @Autowired
    private DayWiseScheduleRepository dayWiseScheduleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * ✅ Check if a Day-Wise Schedule exists for the user and subject.
     */
    public Optional<DayWiseSchedule> findByUserIdAndSubject(String userId, String subject) {
        return dayWiseScheduleRepository.findByUserIdAndSubject(userId, subject);
    }

    /**
     * ✅ Save the new Day-Wise Schedule if it doesn't exist already.
     */
    public DayWiseSchedule saveSchedule(DayWiseSchedule schedule) {
        return dayWiseScheduleRepository.save(schedule);
    }

    /**
     * ✅ Fetch today's schedule based on start date and day-wise mapping.
     */
    public Map<String, Object> getTodaySchedule(String userId, LocalDate today) {
        logger.info("Fetching today's schedule for user '{}' on date '{}'", userId, today);

        // ✅ Fetch all schedules for the user
        List<DayWiseSchedule> schedules = dayWiseScheduleRepository.findByUserId(userId);
        
        if (schedules.isEmpty()) {
            logger.warn("No schedules found for user '{}'", userId);
            return Map.of("message", "No schedules found for user.");
        }

        logger.info("Total schedules retrieved: {}", schedules.size());

        // ✅ List to hold today's schedules from different subjects
        List<Map<String, Object>> todaySchedules = new ArrayList<>();

        for (DayWiseSchedule schedule : schedules) {
            if (schedule.getStartDate() == null) {
                logger.warn("Skipping schedule ID '{}' as start_date is NULL", schedule.getId());
                continue;
            }

            logger.info("Processing schedule ID '{}' with start_date '{}'", schedule.getId(), schedule.getStartDate());

            // ✅ Calculate which "Day X" it is based on start_date
            long daysElapsed = ChronoUnit.DAYS.between(schedule.getStartDate(), today);
            String dayKey = "Day " + (daysElapsed + 1); // Example: "Day 1", "Day 2", etc.

            logger.info("Looking for key '{}' in JSON data", dayKey);

            try {
                // ✅ Parse JSON from database
                JsonNode scheduleJson = objectMapper.readTree(schedule.getDayWiseData());

                if (scheduleJson.has(dayKey)) {
                    JsonNode todaySchedule = scheduleJson.get(dayKey);
                    JsonNode pods = todaySchedule.get("pods");

                    List<Map<String, Object>> podList = new ArrayList<>();
                    List<Map<String, Object>> quizList = new ArrayList<>();

                    if (pods != null && pods.isArray()) {
                        for (JsonNode pod : pods) {
                            // ✅ Extract Key Concepts
                            List<String> keyConcepts = new ArrayList<>();
                            if (pod.has("key_concepts") && pod.get("key_concepts").isArray()) {
                                for (JsonNode concept : pod.get("key_concepts")) {
                                    keyConcepts.add(concept.asText());
                                }
                            }

                            // ✅ Extract Quiz
                            if (pod.has("quiz") && pod.get("quiz").isArray()) {
                                for (JsonNode quizItem : pod.get("quiz")) {
                                    Map<String, Object> quizMap = new HashMap<>();
                                    quizMap.put("question", quizItem.get("question").asText());
                                    List<String> options = new ArrayList<>();
                                    for (JsonNode option : quizItem.get("options")) {
                                        options.add(option.asText());
                                    }
                                    quizMap.put("options", options);
                                    quizMap.put("answer", quizItem.get("answer").asText());
                                    quizList.add(quizMap);
                                }
                            }

                            // ✅ Store each learning pod
                            Map<String, Object> podData = new HashMap<>();
                            podData.put("title", pod.get("title").asText());
                            podData.put("key_concepts", keyConcepts);
                            podList.add(podData);
                        }
                    }

                    // ✅ Structure the final response
                    Map<String, Object> scheduleData = new HashMap<>();
                    scheduleData.put("topic", schedule.getSubject());
                    scheduleData.put("micro_learning_pod", podList);
                    scheduleData.put("quiz", quizList);

                    todaySchedules.add(scheduleData);
                } else {
                    logger.warn("No data found for '{}' in schedule ID '{}'", dayKey, schedule.getId());
                }
            } catch (JsonProcessingException e) {
                logger.error("Error parsing JSON for schedule ID '{}': {}", schedule.getId(), e.getMessage());
            }
        }

        // ✅ If no schedules were found for today
        if (todaySchedules.isEmpty()) {
            logger.warn("No schedule found for today for user '{}'", userId);
            return Map.of("message", "No schedule found for today.");
        }

        return Map.of("todaySchedule", todaySchedules);
    }
}
