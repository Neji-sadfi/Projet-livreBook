package com.example.livrebook.model.reclamation;

public class FeedBack {
    private int id;
    private String nom;
    private String email;
    private String description;

    public FeedBack(int id, String nom, String email, String description) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.description = description;
    }

    public FeedBack() {

    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Méthode pour afficher les détails du feedback
    @Override
    public String toString() {
        return "FeedBack{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
