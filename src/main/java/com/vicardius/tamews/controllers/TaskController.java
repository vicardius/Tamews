package com.vicardius.tamews.controllers;

import com.vicardius.tamews.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/projects/{projectId}/t/{idTask}")
    public String deleteTask(@PathVariable Long idTask) {
        taskRepository.deleteById(idTask);
        return "redirect:/projects/{projectId}";
    }

}