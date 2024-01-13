package com.example.livrebook.service.eventServices;

import com.example.livrebook.model.event.Event;

import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventService implements CRUD<Event> {
    private Connection cnx;

    public EventService(){
        cnx = DbConnection.getInstance().getCnx();
    }
    @Override
    public boolean insert(Event event) throws SQLException {
        String req = "INSERT INTO event (id,title, adresse, description, picture, startDate, endDate) " +
                "VALUES ('" + event.getId() + "','"  + event.getTitle() + "','" + event.getAdresse() + "','" + event.getDescription() +"','" + event.getPicture()
                +"','" + event.getStartDate() +"','" + event.getEndDate() + "')";


        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }



    @Override
    public boolean update(Event event) throws SQLException {

        String req = "UPDATE event SET " +
                "title='" + event.getTitle() + "', " +
                "adresse='" + event.getAdresse() + "', " +
                "description='" + event.getDescription() + "', " +
                "picture='" + event.getPicture() + "', " +
                "startDate='" + event.getStartDate() + "', " +
                "endDate='" + event.getEndDate() + "' " +
                "WHERE id=" + event.getId();

        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }


    @Override
    public boolean delete(int id) throws SQLException {
        String req = "DELETE FROM event WHERE id=" + id;

        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }



    @Override

    public boolean delete(Event event) throws SQLException {
        String req = "DELETE FROM event WHERE id = "+ event.getId();
        Statement st = cnx.createStatement();

        return st.executeUpdate(req) == -1;
    }


    @Override
    public List<Event> selectAll() throws SQLException {
        List<Event> events = new ArrayList<>();

        String req = "SELECT * FROM event";
        Statement st = cnx.createStatement();
        ResultSet resultSet = st.executeQuery(req);

        while (resultSet.next()) {
            Event event = new Event();
            event.setId(resultSet.getInt("id"));
            event.setTitle(resultSet.getString("title"));
            event.setAdresse(resultSet.getString("adresse"));
            event.setDescription(resultSet.getString("description"));
            event.setPicture(resultSet.getString("picture"));
            event.setStartDate(resultSet.getDate("startDate"));
            event.setEndDate(resultSet.getDate("endDate"));

            // Assuming there is a proper constructor for List<Integer>


            events.add(event);
        }

        return events;
    }




}
