package com.vicardius.tamews.controllers;

import com.vicardius.tamews.models.*;
import com.vicardius.tamews.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskBarRepository taskBarRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(value = {"/", "/projects"})
    public String showAllProjects(Model model) {
        User user = userRepository.findByUsername(getIdUser());
        Set<Project> projects = projectRepository.findByUsers(user);
        model.addAttribute("projects", projects);
        return "/projects";
    }

    public String getIdUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @GetMapping("/projects/{projectId}")
    public String showProject(Model model, @PathVariable Long projectId) {
        Project project = projectRepository.findByProjectId(projectId);
        List<TaskBar> taskBars = taskBarRepository.findByProject(project);

        Map<TaskBar, List<Task>> tasksForTaskBar = new TreeMap<>(new Comparator<TaskBar>() {
            @Override
            public int compare(TaskBar o1, TaskBar o2) {
                return o1.getPositionTaskBar() - o2.getPositionTaskBar();
            }
        });

        for (TaskBar taskBar : taskBars) {
            List<Task> tasksInBar = taskRepository.findByTaskBar(taskBar);
            tasksForTaskBar.put(taskBar, tasksInBar);

        }

        model.addAttribute("foundProject", project);
        model.addAttribute("taskBarsWithTasks", tasksForTaskBar);
        model.addAttribute("taskBar", taskBars);
        return "project";
    }

    @GetMapping("/projects/p/{projectId}")
    public String deleteProject(@PathVariable Long projectId) {
        Project project = projectRepository.findByProjectId(projectId);
        User user = userRepository.findByUsername(getIdUser());
        Set<Project> projects = user.getProjects();
        projects.remove(project);

        Iterable<TaskBar> taskBars = taskBarRepository.findByProject(project);
        Iterable<Task> tasks = taskRepository.findByProject(project);
        for (Task task: tasks) {
            taskRepository.deleteById(task.getIdTask());
        }
        for (TaskBar taskBar: taskBars) {
            taskBarRepository.deleteById(taskBar.getIdTaskBar());
        }
        projectRepository.deleteById(projectId);
        return "redirect:/projects";
    }

    @PostMapping(value = {"/", "projects"})
    public String addNewProject(@RequestParam String projectTitle, @RequestParam String projectDescription, @RequestParam String projectStatus){
        Project project = new Project(projectTitle, projectDescription, projectStatus);
        User user = userRepository.findByUsername(getIdUser());
        Set<User> users = project.getUsers();
        users.add(user);
        project.setUsers(users);

        Set<Project> projects = user.getProjects();
        projects.add(project);
        user.setProjects(projects);

        projectRepository.save(project);
        userRepository.save(user);
        return "redirect:/projects";
    }

    @GetMapping("/findTaskBar/{idTaskBar}")
    @ResponseBody
    public List findTaskBar(@PathVariable Long idTaskBar){
        List list = new ArrayList();
        TaskBar taskBar = taskBarRepository.findByIdTaskBar(idTaskBar);
        list.add(taskBar.getIdTaskBar());
        list.add(taskBar.getTitleTaskBar());
        return list;
    }

    @PostMapping("/saveTaskBar")
    public String saveTaskBar(@RequestParam("idTaskBar") Long idTaskBar, @RequestParam("titleTaskBar") String titleTaskBar){
        TaskBar taskBar = taskBarRepository.findByIdTaskBar(idTaskBar);
        taskBar.setTitleTaskBar(titleTaskBar);
        taskBarRepository.save(taskBar);
        Project project = taskBar.getProject();
        Long idProject = project.getProjectId();
        return "redirect:/projects/" + idProject;
    }

    @GetMapping("/editTask/{idTask}")
    public String editTask(@PathVariable Long idTask, Model model){
        Task task = taskRepository.findByIdTask(idTask);
        Iterable<Comment> comments = commentRepository.findByTask(task);
        model.addAttribute("task", task);
        model.addAttribute("comments", comments);
        return "editTask";
    }

    @PostMapping("/saveTask/{idTask}")
    public String savaTask(@PathVariable Long idTask, @RequestParam("titleTask") String titleTask, @RequestParam("descriptionTask") String descriptionTask){
        Task task = taskRepository.findByIdTask(idTask);
        task.setTitleTask(titleTask);
        task.setDescriptionTask(descriptionTask);
        taskRepository.save(task);
        Project project = task.getProject();
        Long idProject = project.getProjectId();
        return "redirect:/projects/" + idProject;
    }

}