package com.example.livrebook.model.event;

public class Ticket {
    private int id;
    private String QRCODE;
    private int price;
    private boolean isPayed;
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

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public Ticket() {
    }

    public Ticket(int id, String QRCODE, int price, boolean isPayed,int idevent) {
        this.id = id;
        this.QRCODE = QRCODE;
        this.price = price;
        this.isPayed = isPayed;
        this.idevent=idevent;
    }

    public Ticket(String QRCODE, int price, boolean isPayed, int idevent) {
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
