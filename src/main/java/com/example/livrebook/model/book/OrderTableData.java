package com.example.livrebook.model.book;

import java.util.Date;

public class OrderTableData {
    private int orderId;
    private String userId;
    private String email;
    private Date orderDate;
    private String orderStatus;

    private String paymentMethod;
    private int totalPrice;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public OrderTableData(int orderId,String userId, String email, Date orderDate, String orderStatus, String paymentMethod, int totalPrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.email = email;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
    }
    public OrderTableData(String userId, String email, Date orderDate, String orderStatus, String paymentMethod, int totalPrice) {
        this.userId = userId;
        this.email = email;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
    }

    // Ajoutez les getters nécessaires
    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

}
