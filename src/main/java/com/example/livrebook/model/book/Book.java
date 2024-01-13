package com.example.livrebook.model.book;

import java.sql.Date;

public class Book {
    private int id;
    private String title;
    private String author;
    private String category;
    private String language;
    private int nbPages;
    private int quantity;
    private int price;
    private String summary;
    private Date datePublication;
    private String picture;

    public Book() {
    }

    public Book(String title, String author, String category, String language, int nbPages, int quantity, int price, String summary, Date datePublication, String picture) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.language = language;
        this.nbPages = nbPages;
        this.quantity = quantity;
        this.price = price;
        this.summary = summary;
        this.datePublication = datePublication;
        this.picture = picture;
    }

    public Book(int id, String title, String author, String category, String language, int nbPages, int quantity, int price, String summary, Date datePublication, String picture) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.language = language;
        this.nbPages = nbPages;
        this.quantity = quantity;
        this.price = price;
        this.summary = summary;
        this.datePublication = datePublication;
        this.picture = picture;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getNbPages() {
        return nbPages;
    }

    public void setNbPages(int nbPages) {
        this.nbPages = nbPages;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", language='" + language + '\'' +
                ", nbPages=" + nbPages +
                ", quantity=" + quantity +
                ", price=" + price +
                ", summary='" + summary + '\'' +
                ", datePublication=" + datePublication +
                ", picture='" + picture + '\'' +
                '}';
    }
}
