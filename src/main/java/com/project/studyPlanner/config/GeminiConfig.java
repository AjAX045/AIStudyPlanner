package com.project.studyPlanner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;

@Configuration
@Getter
public class GeminiConfig {

	@Value("${gemini.api.url}")
	private String geminiApiUrl;

	@Value("${gemini.api.model:generation/gemini-1.5-pro-002}")
	private String geminiApiModel;


    @Value("${gemini.api.key}")
    private String geminiApiKey;

    // ✅ Returns the full API URL without needing extra modifications in the service class
    public String getFullApiUrl() {
        return geminiApiUrl;
    }

    // ✅ Returns the API Key for authentication
    public String getAuthorizationHeader() {
        return "Bearer " + geminiApiKey;
    }
    
 // ✅ Manually add getter methods (if Lombok is not working correctly)
    public String getGeminiApiUrl() {
        return geminiApiUrl;
    }

    public String getGeminiApiKey() {
        return geminiApiKey;
    }
}
