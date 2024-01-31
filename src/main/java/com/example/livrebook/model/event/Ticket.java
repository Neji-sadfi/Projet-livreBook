package com.example.livrebook.model.event;

public class Ticket {
    private int id;
    private String QRCODE;
    private int price;
    private int isPayed;
    private int idevent;




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

    public int getIdevent() {
        return idevent;
    }

    public void setIdevent(int idevent) {
        this.idevent = idevent;
    }

    public int getIsPayed() {
        return isPayed;
    }

    public void setIsPayed(int payed) {
        isPayed = payed;
    }

    public Ticket() {
    }

    public Ticket(int id, String QRCODE, int price, int isPayed,int idevent) {
        this.id = id;
        this.QRCODE = QRCODE;
        this.price = price;
        this.isPayed = isPayed;
        this.idevent=idevent;
    }

    public Ticket(String QRCODE, int price, int isPayed, int idevent) {
        this.QRCODE = QRCODE;
        this.price = price;
        this.isPayed = isPayed;
        this.idevent=idevent;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", QRCODE='" + QRCODE + '\'' +
                ", price=" + price +
                ", isPayed=" + isPayed +
                ", idevent=" + idevent +
                '}';
    }
}
