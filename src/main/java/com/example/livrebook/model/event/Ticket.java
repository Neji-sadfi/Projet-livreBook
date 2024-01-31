package com.example.livrebook.model.event;

public class Ticket {
    private int id;
    private String QRCODE;
    private int price;
<<<<<<< HEAD
    private int isPayed;
    private int idevent;



=======
    private boolean isPayed;
>>>>>>> 6174e16e975e09bbc1059011add929c71cbeac49

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

<<<<<<< HEAD
    public int getIdevent() {
        return idevent;
    }

    public void setIdevent(int idevent) {
        this.idevent = idevent;
    }

    public int getIsPayed() {
=======
    public boolean isPayed() {
>>>>>>> 6174e16e975e09bbc1059011add929c71cbeac49
        return isPayed;
    }

    public void setIsPayed(int payed) {
        isPayed = payed;
    }

    public Ticket() {
    }

<<<<<<< HEAD
    public Ticket(int id, String QRCODE, int price, int isPayed,int idevent) {
=======
    public Ticket(int id, String QRCODE, int price, boolean isPayed) {
>>>>>>> 6174e16e975e09bbc1059011add929c71cbeac49
        this.id = id;
        this.QRCODE = QRCODE;
        this.price = price;
        this.isPayed = isPayed;
    }

<<<<<<< HEAD
    public Ticket(String QRCODE, int price, int isPayed, int idevent) {
=======
    public Ticket(String QRCODE, int price, boolean isPayed) {
>>>>>>> 6174e16e975e09bbc1059011add929c71cbeac49
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
