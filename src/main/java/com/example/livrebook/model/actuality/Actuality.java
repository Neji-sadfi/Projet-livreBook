package com.example.livrebook.model.actuality;

import java.util.Date;

public class Actuality {
    private int id;
    private String title;
    private String description;
    private Date date;
    private Date publicationDate;

    public Actuality(int id, String title, String description, Date date, Date publicationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.publicationDate = publicationDate;
    }

    public Actuality(String title, String description, Date date, Date publicationDate) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.publicationDate = publicationDate;
    }

    public Actuality() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "Actuality{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", publicationDate=" + publicationDate +
                '}';
    }
}
