package com.project.studyPlanner.ai;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.HashMap;

@Service
public class AIService {
	private final String AI_API_URL = "http://127.0.0.1:5000/generate-study-plan"; // Replaced with real AI API

    public String getStudyPlan(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> request = new HashMap<>();
        request.put("userId", userId);
        return restTemplate.postForObject(AI_API_URL, request, String.class);
    }
}
