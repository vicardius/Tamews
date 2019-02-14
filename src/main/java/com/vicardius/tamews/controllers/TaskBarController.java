package com.vicardius.tamews.controllers;

import com.vicardius.tamews.models.Project;
import com.vicardius.tamews.models.Task;
import com.vicardius.tamews.models.TaskBar;
import com.vicardius.tamews.repositories.ProjectRepository;
import com.vicardius.tamews.repositories.TaskBarRepository;
import com.vicardius.tamews.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskBarController {

    @Autowired
    private TaskBarRepository taskBarRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/projects/{projectId}")
    public String addTaskBar(@RequestParam(name = "titleTaskBar", required = false, defaultValue = "") String titleTaskBar,
                             @RequestParam(name = "positionTaskBar", required = false, defaultValue = "") Integer positionTaskBar,
                             @PathVariable Long projectId,
                             @RequestParam(name = "titleTask", required = false, defaultValue = "") String titleTask,
                             @RequestParam(name = "descriptionTask", required = false, defaultValue = "") String descriptionTask,
                             @RequestParam(name = "idTaskBar", required = false, defaultValue = "") Long taskBarId) {
        if (titleTaskBar.equals("") && titleTaskBar.equals("")) {
            TaskBar taskBar = taskBarRepository.findByIdTaskBar(taskBarId);
            Project project = projectRepository.findByProjectId(projectId);
            Task task = new Task(titleTask, descriptionTask, taskBar, project);
            taskRepository.save(task);
        } else {
            Project project = projectRepository.findByProjectId(projectId);
            TaskBar taskBar = new TaskBar(titleTaskBar, positionTaskBar, project);
            taskBarRepository.save(taskBar);
        }
        return "redirect:/projects/{projectId}";
    }

    @GetMapping("/projects/{projectId}/tb/{idTaskBar}")
    public String deleteTaskBar(@PathVariable Long projectId, @PathVariable Long idTaskBar) {
        TaskBar taskbar = taskBarRepository.findByIdTaskBar(idTaskBar);
        Iterable<Task> allTasksInTaskBar = taskRepository.findByTaskBar(taskbar);
        for (Task task : allTasksInTaskBar) {
            Long taskId = task.getIdTask();
            taskRepository.deleteById(taskId);
        }
        taskBarRepository.deleteById(idTaskBar);
        return "redirect:/projects/{projectId}";
    }

}