package com.project.studyPlanner.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatbotService {

    private final WebClient webClient;
    private final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro:generateContent";
    private final String API_KEY = "AIzaSyAB37LoW88Uy4XKa85WQHDNbuPUi1hKHr4"; // Replace with your actual API key

    public ChatbotService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(GEMINI_API_URL).build();
    }

    // Accepts chat history instead of a single message
    public Mono<String> getChatbotResponse(List<Map<String, String>> chatHistory) {
        // Convert chat history into a structured conversation context
        String conversationContext = chatHistory.stream()
            .map(entry -> entry.get("role") + ": " + entry.get("content"))
            .collect(Collectors.joining("\n"));

        // Prepare request body
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of(
                        "parts", List.of(Map.of("text", conversationContext))
                ))
        );

        return webClient.post()
                .uri("?key=" + API_KEY)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(this::extractFormattedResponse)
                .onErrorResume(e -> Mono.just("⚠️ Error: Unable to fetch response from Gemini API. Please try again."));
    }

    private String extractFormattedResponse(Map<String, Object> response) {
        try {
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
                List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                if (parts != null && !parts.isEmpty()) {
                    String rawText = parts.get(0).get("text").toString();
                    return formatResponse(rawText);
                }
            }
            return "🤖 Sorry, I couldn't generate a proper response. Please try again!";
        } catch (Exception e) {
            return "⚠️ Error: Unable to parse Gemini API response.";
        }
    }

    private String formatResponse(String text) {
        return text.replace("\n\n", "\n") // Remove double newlines
                   .replace("*", "•")     // Replace markdown bullets with proper bullet points
                   .trim();               // Trim spaces
    }
}
