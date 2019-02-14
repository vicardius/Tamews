package com.vicardius.tamews.models;

import javax.persistence.*;

@Entity
public class TaskBar {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idTaskBar;
    private String titleTaskBar;
    private Integer positionTaskBar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="project_id")
    private Project project;

    public TaskBar() {

    }

    public TaskBar(String titleTaskBar, Integer positionTaskBar, Project project) {
        this.titleTaskBar = titleTaskBar;
        this.positionTaskBar = positionTaskBar;
        this.project = project;
    }

    public Long getIdTaskBar() {
        return idTaskBar;
    }

    public void setIdTaskBar(Long idTaskBar) {
        this.idTaskBar = idTaskBar;
    }

    public String getTitleTaskBar() {
        return titleTaskBar;
    }

    public void setTitleTaskBar(String titleTaskBar) {
        this.titleTaskBar = titleTaskBar;
    }

    public Integer getPositionTaskBar() {
        return positionTaskBar;
    }

    public void setPositionTaskBar(Integer positionTaskBar) {
        this.positionTaskBar = positionTaskBar;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}