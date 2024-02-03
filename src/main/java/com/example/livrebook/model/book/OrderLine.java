package com.example.livrebook.model.book;

public class OrderLine {
    private int id;
    private int idOrder;
    private int quantity;
    private int bookId;

    public OrderLine() {
    }

    public OrderLine(int id, int idOrder, int quantity, int bookId) {
        this.id = id;
        this.idOrder = idOrder;
        this.quantity = quantity;
        this.bookId = bookId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "id=" + id +
                ", idOrder=" + idOrder +
                ", quantity=" + quantity +
                ", bookId=" + bookId +
                '}';
    }
}
