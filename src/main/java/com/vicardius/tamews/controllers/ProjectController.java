package com.vicardius.tamews.controllers;

import com.vicardius.tamews.models.*;
import com.vicardius.tamews.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    public String getIdUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @GetMapping(value = {"/", "/projects"})
    public String showAllProjects(Model model) {
        User user = userRepository.findByUsername(getIdUser());
        Set<Project> projects = projectRepository.findByUsers(user);
        model.addAttribute("projects", projects);
        return "/projects";
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

    @PostMapping("/saveProject")
    public String saveProject(@RequestParam("projectId") Long projectId, @RequestParam("projectTitle") String projectTitle, @RequestParam("projectDescription") String projectDescription, @RequestParam("projectStatus") String projectStatus){
        Project project = projectRepository.findByProjectId(projectId);
        project.setProjectTitle(projectTitle);
        project.setProjectDescription(projectDescription);
        project.setProjectStatus(projectStatus);
        projectRepository.save(project);
        return "redirect:/projects/";
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
        for (TaskBar taskBar : taskBars) {
            Iterable<Task> allTasksInTaskBar = taskRepository.findByTaskBar(taskBar);
            for (Task task : allTasksInTaskBar) {
                Iterable<Comment> allCommentsInTask = commentRepository.findByTask(task);
                for (Comment comment : allCommentsInTask) {
                    commentRepository.deleteById(comment.getIdComment());
                }
                taskRepository.deleteById(task.getIdTask());
            }
            taskBarRepository.deleteById(taskBar.getIdTaskBar());
        }
        projectRepository.deleteById(projectId);
        return "redirect:/projects";
    }

    @GetMapping("/projects/pe/{projectId}")
    @ResponseBody
    public List editProject(@PathVariable Long projectId) {
        List list = new ArrayList();
        Project project = projectRepository.findByProjectId(projectId);
        list.add(project.getProjectId());
        list.add(project.getProjectTitle());
        list.add(project.getProjectDescription());
        list.add(project.getProjectStatus());
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

//    @GetMapping("/editTask/{idTask}")
//    @ResponseBody
//    public List editTask(@PathVariable Long idTask, Model model){
//        List list = new ArrayList();
//        Task task = taskRepository.findByIdTask(idTask);
//        Iterable<Comment> comments = commentRepository.findByTask(task);
//        list.add(task.getTitleTask());
//        list.add(task.getDescriptionTask());
//        list.add(comments);
//
//
//        return list;
//    }

    @GetMapping("/findTaskBar/{idTaskBar}")
    @ResponseBody
    public List findTaskBar(@PathVariable Long idTaskBar){
        List list = new ArrayList();
        TaskBar taskBar = taskBarRepository.findByIdTaskBar(idTaskBar);
        list.add(taskBar.getIdTaskBar());
        list.add(taskBar.getTitleTaskBar());
        return list;
    }

//    @PostMapping("/saveTask/{idTask}")
//    public String saveTask(@PathVariable Long idTask, @RequestParam("titleTask") String titleTask, @RequestParam("descriptionTask") String descriptionTask){
//        Task task = taskRepository.findByIdTask(idTask);
//        task.setTitleTask(titleTask);
//        task.setDescriptionTask(descriptionTask);
//        taskRepository.save(task);
//        Project project = task.getProject();
//        Long idProject = project.getProjectId();
//        return "redirect:/projects/" + idProject;
//    }

    @GetMapping(value = "/task/{idTask}")
    public String openTask(@PathVariable("idTask") Long idTask, Model model) {
        Task task = taskRepository.findByIdTask(idTask);
        Iterable<Comment> comments = commentRepository.findByTask(task);
        model.addAttribute("task", task);
        model.addAttribute("comments", comments);
        return "fragment/task :: taskFragment";
    }

    @GetMapping(value = "/drag-task")
    @ResponseBody
    public void dragTask(@RequestParam("idTask") Long idTask, @RequestParam("idTaskBar") Long idTaskBar) {
        Task task = taskRepository.findByIdTask(idTask);
        TaskBar taskBar = taskBarRepository.findByIdTaskBar(idTaskBar);
        task.setTaskBar(taskBar);
        taskRepository.save(task);
    }
}