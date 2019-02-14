package com.vicardius.tamews.repositories;

import com.vicardius.tamews.models.Project;
import com.vicardius.tamews.models.TaskBar;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskBarRepository extends CrudRepository<TaskBar, Long> {

    List<TaskBar> findByProject(Project project);

    TaskBar findByIdTaskBar(Long idTaskBar);

    //Iterable<TaskBar> findByProject(Long idProject);
}
