package com.vicardius.tamews.controllers;

import com.vicardius.tamews.models.Comment;
import com.vicardius.tamews.models.Project;
import com.vicardius.tamews.models.Task;
import com.vicardius.tamews.models.TaskBar;
import com.vicardius.tamews.repositories.CommentRepository;
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

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/projects/{projectId}")
    public String addTaskBar(@RequestParam(name = "titleTaskBar", required = false, defaultValue = "") String titleTaskBar,
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
            Iterable<TaskBar> taskBars = taskBarRepository.findByProject(project);
            int position = 0;
            for (TaskBar taskBar: taskBars) {
                if(taskBar.getPositionTaskBar() > position) {
                    position = taskBar.getPositionTaskBar();
                }
            }
            TaskBar taskBar = new TaskBar(titleTaskBar, position + 1, project);
            taskBarRepository.save(taskBar);
        }
        return "redirect:/projects/{projectId}";
    }

    @GetMapping("/projects/{projectId}/tb/{idTaskBar}")
    public String deleteTaskBar(@PathVariable Long projectId, @PathVariable Long idTaskBar) {
        TaskBar taskbar = taskBarRepository.findByIdTaskBar(idTaskBar);
        Iterable<Task> allTasksInTaskBar = taskRepository.findByTaskBar(taskbar);
        for (Task task : allTasksInTaskBar) {
            Iterable<Comment> allCommentsInTask = commentRepository.findByTask(task);
            for (Comment comment : allCommentsInTask) {
                commentRepository.deleteById(comment.getIdComment());
            }
            taskRepository.deleteById(task.getIdTask());
        }
        taskBarRepository.deleteById(idTaskBar);
        return "redirect:/projects/{projectId}";
    }

}