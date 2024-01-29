package com.example.livrebook.service.eventServices;

import com.example.livrebook.model.event.Ticket;

import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TicketService implements CRUD<Ticket> {
    private Connection cnx;

    public TicketService(){
        cnx = DbConnection.getInstance().getCnx();
    }
    @Override
    public boolean insert(Ticket ticket) throws SQLException {
        String req = "INSERT INTO ticket ( QRCODE, price, isPayed) " +
                "VALUES ('" + ticket.getQRCODE() + "','" + ticket.getPrice()+ "','" + ticket.isPayed() + "')";


        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean update(Ticket ticket) throws SQLException {
        String req = "UPDATE ticket SET " +
                "QRCODE='" + ticket.getQRCODE() + "', " +
                "price=" + ticket.getPrice() + ", " +
                "isPayed=" + ticket.isPayed() +
                " WHERE id=" + ticket.getId();

        try (Statement st = cnx.createStatement()) {
            return st.executeUpdate(req) == -1;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String req = "DELETE FROM ticket WHERE id=" + id;

        try (Statement st = cnx.createStatement()) {
            return st.executeUpdate(req) == -1;
        }
    }

    @Override
    public boolean delete(Ticket ticket) throws SQLException {
        String req = "DELETE FROM ticket WHERE id=" + ticket.getId();

        try (Statement st = cnx.createStatement()) {
            return st.executeUpdate(req) == -1;
        }
    }

    @Override
    public List<Ticket> selectAll() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();

        String req = "SELECT * FROM ticket";
        try (Statement st = cnx.createStatement();
             ResultSet resultSet = st.executeQuery(req)) {

            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(resultSet.getInt("id"));
                ticket.setQRCODE(resultSet.getString("QRCODE"));
                ticket.setPrice(resultSet.getInt("price"));
                ticket.setPayed(resultSet.getBoolean("isPayed"));

                tickets.add(ticket);
            }
        }

        return tickets;
    }
}