package com.vicardius.tamews.controllers;

import com.vicardius.tamews.models.Comment;
import com.vicardius.tamews.models.Project;
import com.vicardius.tamews.models.Task;
import com.vicardius.tamews.models.User;
import com.vicardius.tamews.repositories.CommentRepository;
import com.vicardius.tamews.repositories.TaskRepository;
import com.vicardius.tamews.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentRepository commentRepository;

    public String getIdUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @PostMapping("/addComment/{idTask}")
    public String addComment(@PathVariable Long idTask, @RequestParam("comment") String text) {
        Date date = new Date();
        User user = userRepository.findByUsername(getIdUser());
        Task task = taskRepository.findByIdTask(idTask);
        Comment comment = new Comment(text, date.toString(), task, user);
        commentRepository.save(comment);
        Project project = task.getProject();
        Long idProject = project.getProjectId();
        return "redirect:/projects/" + idProject;
    }

}