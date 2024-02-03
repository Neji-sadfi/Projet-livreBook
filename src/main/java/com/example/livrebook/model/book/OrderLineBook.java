package com.example.livrebook.model.book;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderLineBook {
    //Order
    private int orderId;
    private Date orderDate;
    private String orderStatus;
    private String paymentMethod;
    private int totalPrice;
// book and his quantity

    private Map<Book, OrderLine> bookQuantity;

    // ... getters et setters ...

    public OrderLineBook() {
        this.bookQuantity = new HashMap<>();
    }

    // Ajoutez une méthode pour ajouter une association livre-quantité à la HashMap
    public void addBookQuantity(Book book, OrderLine orderLine) {
        this.bookQuantity.put(book, orderLine);
    }

    // Ajoutez une méthode pour obtenir la HashMap complète
    public Map<Book, OrderLine> getBookQuantity() {
        return bookQuantity;
    }
    public OrderLineBook(int orderId, Date orderDate, String orderStatus, String paymentMethod, int totalPrice, HashMap<Book, OrderLine> bookQuantity) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.bookQuantity = bookQuantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }


    public void setBookQuantity(HashMap<Book, OrderLine> bookQuantity) {
        this.bookQuantity = bookQuantity;
    }


    // Ajoutez une méthode pour obtenir la quantité d'un livre dans la commande
    public int getBookQuantity(Book book) {
        OrderLine orderLine = this.bookQuantity.get(book);
        return (orderLine != null) ? orderLine.getQuantity() : 0;
    }

    // Ajoutez une méthode pour vérifier si un livre est présent dans la commande
    public boolean containsBook(Book book) {
        return this.bookQuantity.containsKey(book);
    }

    // Ajoutez une méthode pour supprimer un livre de la commande
    public void removeBook(Book book) {
        this.bookQuantity.remove(book);
    }

    // Ajoutez une méthode pour vider la liste des livres de la commande
    public void clearBookQuantity() {
        this.bookQuantity.clear();
    }
    @Override
    public String toString() {
        return "OrderLineBook{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", orderStatus='" + orderStatus + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", totalPrice=" + totalPrice +
                ", bookQuantity=" + bookQuantity +
                '}';
    }
}
