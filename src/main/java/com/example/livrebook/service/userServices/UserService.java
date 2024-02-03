package com.example.livrebook.service.userServices;

import com.example.livrebook.model.user.User;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService implements CRUD<User> {
    private Connection cnx;

    public UserService(){
        cnx = DbConnection.getInstance().getCnx();
    }

    @Override
    public boolean insert(User user) throws SQLException {
        String req = "INSERT INTO user (first_name, last_name, email) " +
                "VALUES ('" + user.getFirst_name() + "','" + user.getLast_name() + "','" + user.getEmail() + "')";


        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean update(User user) throws SQLException {
        String req = "UPDATE user SET first_name = '"+user.getFirst_name()+
                "', last_name = '"+ user.getLast_name()+"', email = '"+user.getEmail()+"'"+
                "WHERE id = "+ user.getId();
        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String req = "DELETE FROM user WHERE id = "+id;
        Statement st = cnx.createStatement();

        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean delete(User user) throws SQLException {
        String req = "DELETE FROM user WHERE id = "+user.getId();
        Statement st = cnx.createStatement();

        return st.executeUpdate(req) == -1;
    }

    @Override
    public List<User> selectAll() throws SQLException {
        List<User> users = new ArrayList<User>();
        String req = "SELECT * FROM user";
        Statement st = cnx.createStatement();
        ResultSet resultSet = st.executeQuery(req);
        while(resultSet.next()){
            User u = new User();
            u.setId(resultSet.getInt(1));
            u.setFirst_name(resultSet.getString(2));
            u.setLast_name(resultSet.getString(3));
            u.setEmail(resultSet.getString(4));

            users.add(u);
        }
        return users;
    }

    @Override
    public List<User> selectWherePending() throws SQLException {
        return null;
    }
}
