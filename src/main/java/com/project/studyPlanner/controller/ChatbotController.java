package com.project.studyPlanner.controller;

import com.project.studyPlanner.service.ChatbotService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "*")  // Allow frontend requests
public class ChatbotController {

    private final ChatbotService chatbotService;
    
    // Store chat history for each user (in-memory storage)
    private final Map<String, List<Map<String, String>>> chatHistories = new ConcurrentHashMap<>();
    
    // Define the maximum number of messages to store per user
    private static final int MAX_HISTORY_SIZE = 5;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/ask")
    public Mono<Map<String, String>> askChatbot(@RequestBody Map<String, Object> request) {
        String userMessage = (String) request.get("message");
        String userId = (String) request.getOrDefault("userId", "default_user"); // Track user session

        // Retrieve or initialize chat history
        List<Map<String, String>> history = chatHistories.getOrDefault(userId, new java.util.ArrayList<>());

        // Add new user message to history
        history.add(Map.of("role", "user", "content", userMessage));

        // Ensure chat history does not exceed the max limit
        if (history.size() > MAX_HISTORY_SIZE) {
            history = history.subList(history.size() - MAX_HISTORY_SIZE, history.size());
        }

        // Call service with updated chat history
        List<Map<String, String>> finalHistory = history; // To use inside lambda
        return chatbotService.getChatbotResponse(finalHistory)
                .map(response -> {
                    // Add bot response to history
                    finalHistory.add(Map.of("role", "bot", "content", response));

                    // Save updated history in memory
                    chatHistories.put(userId, finalHistory);

                    return Map.of("response", response);
                });
    }
}
