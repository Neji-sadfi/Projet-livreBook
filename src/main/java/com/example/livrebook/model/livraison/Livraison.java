package com.example.livrebook.model.livraison;

public class Livraison {
    private int id;
    private String status;
    private String adress;
    private int orderId;

    public Livraison() {
    }

    public Livraison(int id, String status, String adress, int orderId) {
        this.id = id;
        this.status = status;
        this.adress = adress;
        this.orderId = orderId;
    }

    public Livraison(String status, String adress, int orderId) {
        this.status = status;
        this.adress = adress;
        this.orderId = orderId;
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

    @Override
    public String toString() {
        return "Livraison{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", adress='" + adress + '\'' +
                ", orderId=" + orderId +
                '}';
    }
}
