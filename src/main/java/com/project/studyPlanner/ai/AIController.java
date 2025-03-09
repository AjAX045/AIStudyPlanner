package com.project.studyPlanner.ai;





import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.studyPlanner.ai.AIService;

@RestController
@RequestMapping("/ai")
public class AIController {
    private final AIService aiService;

    @Autowired
    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/study-plan/{userId}")
    public String getStudyPlan(@PathVariable String userId) {
        return aiService.getStudyPlan(userId);
    }
}


