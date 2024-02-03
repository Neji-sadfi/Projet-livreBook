package com.example.livrebook.model.delivery;

public class Delivery {
    private int id;
    private String status;
    private String adress;
    private int orderId;
    private boolean pending = false;

    public Delivery() {
    }

    public Delivery(int id, String status, String adress, int orderId , boolean pending) {
        this.id = id;
        this.status = status;
        this.adress = adress;
        this.orderId = orderId;
        this.pending = pending;
    }

    public Delivery(String status, String adress, int orderId, boolean pending) {
        this.status = status;
        this.adress = adress;
        this.orderId = orderId;
        this.pending = pending;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return
                "Status='" + status + '\'' +
                ", adress='" + adress + '\'' +
                ", orderId=" + orderId ;
    }
}
