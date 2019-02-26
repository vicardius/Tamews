package com.vicardius.tamews.repositories;

import com.vicardius.tamews.models.Project;
import com.vicardius.tamews.models.Task;
import com.vicardius.tamews.models.TaskBar;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findByTaskBar(TaskBar taskBar);

    Task findByIdTask(Long idTask);

}
