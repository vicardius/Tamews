package com.vicardius.tamews.models;

import javax.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idComment;
    private String textComment;
    private String dateComment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    public Comment() {
    }

    public Comment(String textComment, String dateComment, Task task, User user) {
        this.textComment = textComment;
        this.dateComment = dateComment;
        this.task = task;
        this.user = user;
    }

    public Long getIdComment() {
        return idComment;
    }

    public void setIdComment(Long idComment) {
        this.idComment = idComment;
    }

    public String getTextComment() {
        return textComment;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }

    public String getDateComment() {
        return dateComment;
    }

    public void setDateComment(String dateComment) {
        this.dateComment = dateComment;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}