package com.vicardius.tamews.models;

import javax.persistence.*;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idTask;
    private String titleTask;
    private String descriptionTask;
    private String dueDateTask;
    private String statusTask;
    private int inTaskbarPositionTask;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="taskbar_id")
    private TaskBar taskBar;

    public Task() {

    }

    public Task(String titleTask, String descriptionTask, String dueDateTask, String statusTask, int inTaskbarPositionTask, User user, Project project, TaskBar taskBar) {
        this.titleTask = titleTask;
        this.descriptionTask = descriptionTask;
        this.dueDateTask = dueDateTask;
        this.statusTask = statusTask;
        this.inTaskbarPositionTask = inTaskbarPositionTask;
        this.user = user;
        this.project = project;
        this.taskBar = taskBar;
    }

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public String getTitleTask() {
        return titleTask;
    }

    public void setTitleTask(String titleTask) {
        this.titleTask = titleTask;
    }

    public String getDescriptionTask() {
        return descriptionTask;
    }

    public void setDescriptionTask(String descriptionTask) {
        this.descriptionTask = descriptionTask;
    }

    public String getDueDateTask() {
        return dueDateTask;
    }

    public void setDueDateTask(String dueDateTask) {
        this.dueDateTask = dueDateTask;
    }

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public int getInTaskbarPositionTask() {
        return inTaskbarPositionTask;
    }

    public void setInTaskbarPositionTask(int inTaskbarPositionTask) {
        this.inTaskbarPositionTask = inTaskbarPositionTask;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public TaskBar getTaskBar() {
        return taskBar;
    }

    public void setTaskBar(TaskBar taskBar) {
        this.taskBar = taskBar;
    }

}