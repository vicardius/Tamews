package com.vicardius.tamews.models;

import javax.persistence.*;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idTask;
    private String titleTask;
    private String descriptionTask;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="taskbar_id")
    private TaskBar taskBar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="project_id")
    private Project project;

    public Task() {
    }

    public Task(String titleTask, String descriptionTask, TaskBar taskBar, Project project) {
        this.titleTask = titleTask;
        this.descriptionTask = descriptionTask;
        this.taskBar = taskBar;
        this.project = project;
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

    public TaskBar getTaskBar() {
        return taskBar;
    }

    public void setTaskBar(TaskBar taskBar) {
        this.taskBar = taskBar;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}