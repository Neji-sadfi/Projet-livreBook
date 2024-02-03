package com.example.livrebook.model.book;

public class BookQuantity {
    private int bookQuantityId;
    private int bookId;
    private int bookQuantity;

    public BookQuantity() {
    }

    @Override
    public String toString() {
        return "BookQuantity{" +
                "bookQuantityId=" + bookQuantityId +
                ", bookId=" + bookId +
                ", quantity=" + bookQuantity +
                '}';
    }

    public BookQuantity(int bookQuantityId, int bookId, int bookQuantity) {
        this.bookQuantityId = bookQuantityId;
        this.bookId = bookId;
        this.bookQuantity = bookQuantity;
    }

    public BookQuantity(int bookId, int quantity) {
        this.bookId = bookId;
        this.bookQuantity = quantity;
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

    public int getbookQuantity() {
        return bookQuantity;
    }

    public void setbookQuantity(int quantity) {
        this.bookQuantity = quantity;
    }

}
