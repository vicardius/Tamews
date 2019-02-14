package com.vicardius.tamews.controllers;

import com.vicardius.tamews.models.Comment;
import com.vicardius.tamews.models.Task;
import com.vicardius.tamews.repositories.CommentRepository;
import com.vicardius.tamews.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/projects/{projectId}/t/{idTask}")
    public String deleteTask(@PathVariable Long idTask) {
        Task task = taskRepository.findByIdTask(idTask);
        Iterable<Comment> allCommentsInTask = commentRepository.findByTask(task);
        for (Comment comment : allCommentsInTask) {
            commentRepository.deleteById(comment.getIdComment());
        }
        taskRepository.deleteById(idTask);
        return "redirect:/projects/{projectId}";
    }

}