package com.example.livrebook.model.deliveryOrder;

import com.example.livrebook.model.delivery.Delivery;

import java.sql.Date;

public class DeliveryOrder {
    private int idDelivery;
    private String status;
    private String adress;
    private int orderId;
    private boolean pending = false;
    private int idOrder;
    private int userId;
    private Date orderDate;
    private String orderStatus;
    private String paymentMethod;
    private int totalPrice;
    private String first_name;

    public DeliveryOrder(){}
    public DeliveryOrder(int idDelivery, String status, String adress, int orderId , boolean pending, int idOrder, int userId, Date orderDate, String orderStatus, String paymentMethod, int totalPrice, String first_name) {
        this.idDelivery = idDelivery;
        this.status = status;
        this.adress = adress;
        this.orderId = orderId;
        this.pending = pending;
        this.idOrder = idOrder;
        this.userId = userId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.first_name=first_name;
    }
    public DeliveryOrder(String status, String adress, int orderId , boolean pending, int userId, Date orderDate, String orderStatus, String paymentMethod, int totalPrice, String first_name){
        this.status = status;
        this.adress = adress;
        this.orderId = orderId;
        this.pending = pending;
        this.userId = userId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.first_name= first_name;
    }

    public int getIdDelivery() {
        return idDelivery;
    }

    public void setIdDelivery(int idDelivery) {
        this.idDelivery = idDelivery;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String toString() {
        return
                "Status='" + status + '\'' +
                        ", adresse='" + adress + '\'' +
                        ", nom=" + first_name + '\'' +
                        ", date=" + orderDate + '\'' +
                        ", commande='" + orderStatus + '\'' +
                        ", payment='" + paymentMethod + '\'' +
                        ", totale=" + totalPrice ;
    }

}
