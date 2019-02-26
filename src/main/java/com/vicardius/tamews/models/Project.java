package com.vicardius.tamews.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long projectId;
    private String projectTitle;
    private String projectDescription;
    private String projectStatus;

    @ManyToMany(mappedBy = "projects")
    @JsonIgnoreProperties("projects")
    private Set<User> users = new HashSet<>();

    public Project() {

    }

    public Project(String projectTitle, String projectDescription, String projectStatus) {
        this.projectTitle = projectTitle;
        this.projectDescription = projectDescription;
        this.projectStatus = projectStatus;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}