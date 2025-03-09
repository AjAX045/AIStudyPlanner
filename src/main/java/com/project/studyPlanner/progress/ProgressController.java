package com.project.studyPlanner.progress;



import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.project.studyPlanner.progress.Progress;
import com.project.studyPlanner.progress.ProgressRepository;

@RestController
@RequestMapping("/progress")
public class ProgressController {
    private final ProgressRepository progressRepository;

    @Autowired
    public ProgressController(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @GetMapping("/{userId}")
    public List<Progress> getUserProgress(@PathVariable String userId) {
        return progressRepository.findByUserId(userId);
    }
}

