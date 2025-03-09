package com.project.studyPlanner.learningpod;



import org.springframework.data.jpa.repository.JpaRepository;
import com.project.studyPlanner.learningpod.LearningPod;
import java.util.List;

public interface LearningPodRepository extends JpaRepository<LearningPod, Long> {
    List<LearningPod> findByTopic(String topic);
}

