package com.example.livrebook.model.book;

import java.util.Date;

public class BookQuantityDetails {
    private int bookQuantityId;
    private int bookId;
    private int bookQuantity;

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
    private int totalPrice ;

    public BookQuantityDetails() {
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "BookQuantityDetails{" +
                "bookQuantityId=" + bookQuantityId +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", language='" + language + '\'' +
                ", nbPages=" + nbPages +
                ", bookQuantity=" + bookQuantity +
                ", price=" + price +
                ", summary='" + summary + '\'' +
                ", datePublication=" + datePublication +
                ", picture='" + picture + '\'' +
                '}';
    }

    public int getBookQuantityId() {
        return bookQuantityId;
    }

    public void setBookQuantityId(int bookQuantityId) {
        this.bookQuantityId = bookQuantityId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
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
}
