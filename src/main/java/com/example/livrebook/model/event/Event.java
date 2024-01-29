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
    private List<Ticket> Tickets;
    private int nb_ticket;


    public Event(String title, String adresse, String description, String picture, Date startDate, Date endDate, int nb_ticket) {
        this.title = title;
        this.adresse = adresse;
        this.description = description;
        this.picture = picture;
        this.startDate = startDate;
        this.endDate = endDate;
       this.nb_ticket=nb_ticket;
    }
    public Event(int id ,String title, String adresse, String description, String picture, Date startDate, Date endDate) {
        this.id=id;
        this.title = title;
        this.adresse = adresse;
        this.description = description;
        this.picture = picture;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    public Event() {

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

    public List<Ticket> getIdTickets() {
        return Tickets;
    }

    public void setTickets(List<Ticket> Tickets) {
        this.Tickets = Tickets;
    }

    public int getNb_ticket() {
        return nb_ticket;
    }

    public void setNb_ticket(int nb_ticket) {
        this.nb_ticket = nb_ticket;
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
                ", nombre de Tickets=" + nb_ticket +
                '}';
    }


}
