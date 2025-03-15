package com.project.studyPlanner.controller;

import com.project.studyPlanner.models.StudyPlan;
import com.project.studyPlanner.repository.StudyPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/studyplan")
@CrossOrigin(origins = "*")
public class StudyPlanController {

    @Autowired
    private StudyPlanRepository studyPlanRepository;

    // ✅ Save Study Plan
    @PostMapping("/save")
    public ResponseEntity<String> saveStudyPlan(@RequestBody StudyPlan studyPlan) {
        try {
            studyPlan.setCreatedAt(LocalDateTime.now()); // Set timestamp
            studyPlanRepository.save(studyPlan);
            return ResponseEntity.ok("Study Plan Saved Successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving study plan: " + e.getMessage());
        }
    }

    // ✅ Get All Study Plans
    @GetMapping("/all")
    public ResponseEntity<List<StudyPlan>> getAllStudyPlans() {
        return ResponseEntity.ok(studyPlanRepository.findAll());
    }
    
 // ✅ Get Study Plan by ID
    @GetMapping("/{id}")
    public ResponseEntity<StudyPlan> getStudyPlanById(@PathVariable Long id) {
        return studyPlanRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
 // ✅ Delete Study Plan by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudyPlan(@PathVariable Long id) {
        if (studyPlanRepository.existsById(id)) {
            studyPlanRepository.deleteById(id);
            return ResponseEntity.ok("Study Plan deleted successfully!");
        } else {
            return ResponseEntity.badRequest().body("Study Plan not found!");
        }
    }



}
