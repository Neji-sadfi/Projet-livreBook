package com.example.livrebook.model.actuality;

public class Comment {
    private int id;
    private String description;
    private int userId;

    public Comment(){};

    public Comment(int id, String description, int userId) {
        this.id = id;
        this.description = description;
        this.userId = userId;
    }

    public Comment(String description, int userId) {
        this.description = description;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                '}';
    }
}
