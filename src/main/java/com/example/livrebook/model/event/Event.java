package com.example.livrebook.model.event;

import java.util.Date;
import java.util.List;

public class Event {
    private int id ;
    private String title;
    private String adresse;
    private String description;
    private String picture;
    private Date startDate;
    private Date endDate;
    private List<Integer> idTickets;

    public Event(int id, String title, String adresse, String description, String picture, Date startDate, Date endDate, List<Integer> idTickets) {
        this.id = id;
        this.title = title;
        this.adresse = adresse;
        this.description = description;
        this.picture = picture;
        this.startDate = startDate;
        this.endDate = endDate;
        this.idTickets = idTickets;
    }

    public Event() {
    }

    public Event(String title, String adresse, String description, String picture, Date startDate, Date endDate, List<Integer> idTickets) {
        this.title = title;
        this.adresse = adresse;
        this.description = description;
        this.picture = picture;
        this.startDate = startDate;
        this.endDate = endDate;
        this.idTickets = idTickets;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Integer> getIdTickets() {
        return idTickets;
    }

    public void setIdTickets(List<Integer> idTickets) {
        this.idTickets = idTickets;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", adresse='" + adresse + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", idTickets=" + idTickets +
                '}';
    }
}
