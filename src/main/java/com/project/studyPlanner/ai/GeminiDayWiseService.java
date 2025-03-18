package com.project.studyPlanner.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.studyPlanner.config.GeminiConfig;
import com.project.studyPlanner.models.StudyPlan;
import com.project.studyPlanner.models.DayWiseSchedule;
import com.project.studyPlanner.repository.DayWiseScheduleRepository;
import com.project.studyPlanner.repository.StudyPlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class GeminiDayWiseService {
    private final GeminiConfig geminiConfig;
    private final RestTemplate restTemplate = new RestTemplate();
    private final DayWiseScheduleRepository dayWiseScheduleRepository;
    private final StudyPlanRepository studyPlanRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(GeminiDayWiseService.class);

    public GeminiDayWiseService(GeminiConfig geminiConfig, DayWiseScheduleRepository dayWiseScheduleRepository, StudyPlanRepository studyPlanRepository) {
        this.geminiConfig = geminiConfig;
        this.dayWiseScheduleRepository = dayWiseScheduleRepository;
        this.studyPlanRepository = studyPlanRepository;
    }

    /**
     * ✅ Generate and store a day-wise schedule with improved AI prompt.
     */
    public DayWiseSchedule generateAndStoreDayWiseSchedule(String subject, String userId) {
        // 🛠 Check if schedule already exists
        Optional<DayWiseSchedule> existingSchedule = dayWiseScheduleRepository.findByUserIdAndSubject(userId, subject);
        if (existingSchedule.isPresent()) {
            logger.info("Day-wise schedule already exists for subject: {}", subject);
            return existingSchedule.get();
        }

        // 🛠 Fetch Study Plan by Subject
        Optional<StudyPlan> studyPlanOptional = studyPlanRepository.findBySubject(subject);
        if (studyPlanOptional.isEmpty()) {
            logger.warn("No study plan found for subject: {}", subject);
            return null;
        }
        StudyPlan studyPlan = studyPlanOptional.get();

        try {
            logger.info("Sending request to Gemini AI for subject '{}'", subject);

            // ✅ Updated AI Prompt
            String prompt = """
                "Convert the given study schedule into JSON format without modifying its content.  
                - Each day should have **3 Micro-Learning Pods**, with topics evenly distributed.  
                - **Preserve all existing links**, distributing them **equally among pods**.  
                - **Add new relevant resources (videos, articles) if necessary** based on the topic.  
                - **Each pod must have at least 3 quizzes** related to its resources.  
                - **Ensure all links are clickable** and properly structured.  

                ### JSON Output Format:
                {
                  "Day 1": {
                    "pods": [
                      {
                        "title": "Pod 1 Title",
                        "key_concepts": ["Concept 1", "Concept 2"],
                        "resources": [
                          {"type": "video", "title": "Video 1", "link": "https://example.com"}
                        ],
                        "quiz": [
                          {"question": "Q1?", "options": ["A", "B", "C", "D"], "answer": "A"}
                        ]
                      },
                      { "title": "Pod 2 Title", "resources": [...], "quiz": [...] },
                      { "title": "Pod 3 Title", "resources": [...], "quiz": [...] }
                    ]
                  },
                  "Day 2": { ... }
                }

                - Keep the schedule **unchanged**—just restructure it into pods.  
                - No fictional links—use **real educational sources**.  
                - **Return JSON only.**"
                """;

            // ✅ Prepare API Request
            Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                    Map.of(
                        "role", "user",
                        "parts", List.of(
                            Map.of("text", prompt + "\n\n" + studyPlan.getStudyData())
                        )
                    )
                )
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            String geminiApiUrl = geminiConfig.getGeminiApiUrl() + "?key=" + geminiConfig.getGeminiApiKey();

            // ✅ Make API call
            ResponseEntity<Map> responseEntity = restTemplate.exchange(
                    geminiApiUrl, HttpMethod.POST, requestEntity, Map.class
            );

            Map<String, Object> response = responseEntity.getBody();

            // ✅ Handle API Response
            if (response == null || response.isEmpty()) {
                logger.warn("Received empty response from Gemini AI for subject: {}", subject);
                return null;
            }

            try {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
                if (candidates == null || candidates.isEmpty()) {
                    logger.error("No 'candidates' found in response");
                    return null;
                }

                Map<String, Object> firstCandidate = candidates.get(0);
                Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
                List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");

                if (parts == null || parts.isEmpty()) {
                    logger.error("No 'parts' found in response");
                    return null;
                }

                String responseText = (String) parts.get(0).get("text");

                // ✅ Clean JSON formatting
                responseText = responseText.replaceAll("```json", "").replaceAll("```", "").trim();

                logger.info("Generated AI schedule: {}", responseText);

                // ✅ Save the generated schedule
                DayWiseSchedule newSchedule = new DayWiseSchedule();
                newSchedule.setUserId(userId);
                newSchedule.setSubject(subject);
                newSchedule.setStartDate(LocalDate.now()); // ✅ Store today's date as startDate
                newSchedule.setDayWiseData(responseText);

                DayWiseSchedule savedSchedule = dayWiseScheduleRepository.save(newSchedule);
                logger.info("Schedule saved for subject '{}'", subject);

                return savedSchedule;

            } catch (ClassCastException e) {
                logger.error("Error parsing API response: {}", e.getMessage());
                return null;
            }

        } catch (HttpClientErrorException e) {
            logger.error("HTTP error while calling Gemini API: {}", e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            logger.error("Connection issue: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
        }

        return null;
    }
}
