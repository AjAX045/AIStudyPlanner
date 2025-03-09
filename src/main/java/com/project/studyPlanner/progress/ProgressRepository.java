package com.project.studyPlanner.progress;




import org.springframework.data.jpa.repository.JpaRepository;
import com.project.studyPlanner.progress.Progress;
import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    List<Progress> findByUserId(String userId);
}
