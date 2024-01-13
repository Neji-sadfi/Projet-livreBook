package com.example.livrebook.model.book;

public class BookQuantity {
    private int bookQuantityId;
    private int bookId;
    private int quantity;

    public BookQuantity() {
    }

    public BookQuantity(int bookQuantityId, int bookId, int quantity) {
        this.bookQuantityId = bookQuantityId;
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public BookQuantity(int bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "BookQuantity{" +
                "bookQuantityId=" + bookQuantityId +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                '}';
    }
}
