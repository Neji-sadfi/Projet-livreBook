package com.example.livrebook.model.event;

public class Ticket {
    private int id;
    private String QRCODE;
    private int price;
    private boolean isPayed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQRCODE() {
        return QRCODE;
    }

    public void setQRCODE(String QRCODE) {
        this.QRCODE = QRCODE;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public Ticket() {
    }

    public Ticket(int id, String QRCODE, int price, boolean isPayed) {
        this.id = id;
        this.QRCODE = QRCODE;
        this.price = price;
        this.isPayed = isPayed;
    }

    public Ticket(String QRCODE, int price, boolean isPayed) {
        this.QRCODE = QRCODE;
        this.price = price;
        this.isPayed = isPayed;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", QRCODE='" + QRCODE + '\'' +
                ", price=" + price +
                ", isPayed=" + isPayed +
                '}';
    }
}
