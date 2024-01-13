package com.example.livrebook.model.book;

public class OrderLine {
    private int id;
    private int idOrder;
    private int idUser;
    private int quantityBookId;

    public OrderLine() {
    }

    public OrderLine(int id, int idOrder, int idUser, int quantityBookId) {
        this.id = id;
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.quantityBookId = quantityBookId;
    }

    public OrderLine(int idOrder, int idUser, int quantityBookId) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.quantityBookId = quantityBookId;
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getQuantityBookId() {
        return quantityBookId;
    }

    public void setQuantityBookId(int quantityBookId) {
        this.quantityBookId = quantityBookId;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "id=" + id +
                ", idOrder=" + idOrder +
                ", idUser=" + idUser +
                ", quantityBookId=" + quantityBookId +
                '}';
    }
}
