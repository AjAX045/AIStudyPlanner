package com.project.studyPlanner.task;



import org.springframework.data.jpa.repository.JpaRepository;
import com.project.studyPlanner.task.Task;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(String userId);
}

