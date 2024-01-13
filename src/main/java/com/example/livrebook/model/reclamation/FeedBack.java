package com.example.livrebook.model.reclamation;

public class FeedBack {
    private int id;
    private String message;
    private int userId;
    private int bookId;

    public FeedBack() {
    }

    public FeedBack(int id, String message, int userId, int bookId) {
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.bookId = bookId;
    }

    public FeedBack(String message, int userId, int bookId) {
        this.message = message;
        this.userId = userId;
        this.bookId = bookId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "FeedBack{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", userId=" + userId +
                ", bookId=" + bookId +
                '}';
    }
}
