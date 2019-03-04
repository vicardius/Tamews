package com.vicardius.tamews.controllers;

import com.vicardius.tamews.models.*;
import com.vicardius.tamews.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskBarController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskBarRepository taskBarRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentRepository commentRepository;

    public String getIdUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @PostMapping("/projects/{projectId}")
    public String addTaskBar(@RequestParam(name = "titleTaskBar", required = false, defaultValue = "") String titleTaskBar,
                             @PathVariable Long projectId,
                             @RequestParam(name = "titleTask", required = false, defaultValue = "") String titleTask,
                             @RequestParam(name = "descriptionTask", required = false, defaultValue = "") String descriptionTask,
                             @RequestParam(name = "dueDateTask", required = false, defaultValue = "") String dueDateTask,
                             @RequestParam(name = "statusTask", required = false, defaultValue = "Active") String statusTask,
                             @RequestParam(name = "idTaskBar", required = false, defaultValue = "") Long taskBarId) {
        if (titleTaskBar.equals("") && titleTaskBar.equals("")) {
            User user = userRepository.findByUsername(getIdUser());
            Project project = projectRepository.findByProjectId(projectId);
            TaskBar taskBar = taskBarRepository.findByIdTaskBar(taskBarId);
            Iterable<Task> tasks = taskRepository.findByTaskBar(taskBar);
            int inTaskbarPositionTask = 0;
            for (Task task: tasks) {
                if(task.getInTaskbarPositionTask() > inTaskbarPositionTask) {
                    inTaskbarPositionTask = task.getInTaskbarPositionTask();
                }
            }
            Task task = new Task(titleTask, descriptionTask, dueDateTask, statusTask, inTaskbarPositionTask + 1, user, project, taskBar);
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