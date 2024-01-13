package com.example.livrebook.service.actualityServices;

import com.example.livrebook.model.actuality.Actuality;
import com.example.livrebook.model.user.User;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ActualityService implements CRUD<Actuality> {
    private Connection cnx;

    public ActualityService(){
        cnx = DbConnection.getInstance().getCnx();
    }
    @Override
    public boolean insert(Actuality actuality) throws SQLException {
        String req = "INSERT INTO actuality (title, description, date) " +
                "VALUES ('" + actuality.getTitle() + "','" + actuality.getDescription() + "','" +
                actuality.getDate()+ "')";


        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean update(Actuality actuality) throws SQLException {
        String req = "UPDATE actuality SET title = '"+actuality.getTitle()+
                "', description = '"+ actuality.getDescription()+"', date = '"+actuality.getDate()+"'"+
                "WHERE id = "+ actuality.getId();
        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;    }

    @Override
    public boolean delete(int id) throws SQLException {
        String req = "DELETE FROM actuality WHERE id = "+id;
        Statement st = cnx.createStatement();

        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean delete(Actuality actuality) throws SQLException {
        String req = "DELETE FROM actuality WHERE id = "+actuality.getId();
        Statement st = cnx.createStatement();

        return st.executeUpdate(req) == -1;

    }

    @Override
    public List<Actuality> selectAll() throws SQLException {
        List<Actuality> actualities = new ArrayList<Actuality>();
        String req = "SELECT * FROM 'actuality'";
        Statement st = cnx.createStatement();
        ResultSet resultSet = st.executeQuery(req);
        while(resultSet.next()){
            Actuality actu = new Actuality();
            actu.setId(resultSet.getInt(1));
            actu.setTitle(resultSet.getString(2));
            actu.setDescription(resultSet.getString(3));
            actu.setDate(resultSet.getDate(4));

            actualities.add(actu);
        }
        return actualities;
    }
}
