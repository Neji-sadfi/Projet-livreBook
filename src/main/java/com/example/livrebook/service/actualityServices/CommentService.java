package com.example.livrebook.service.actualityServices;

import com.example.livrebook.model.actuality.Actuality;
import com.example.livrebook.model.actuality.Comment;
import com.example.livrebook.model.user.User;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CommentService implements CRUD<Comment> {
    private Connection cnx;

    public CommentService(){
        cnx = DbConnection.getInstance().getCnx();
    }
    @Override
    public boolean insert(Comment comment) throws SQLException {
        String req = "INSERT INTO comment (description, userId) " +
                "VALUES ('" + comment.getDescription() + "','" + comment.getUserId()+ "')";
        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean update(Comment comment) throws SQLException {
        String req = "UPDATE comment SET description = '"+comment.getDescription() +
                "', userId = '"+comment.getUserId()+"'"+
                "WHERE id = "+ comment.getId();
        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String req = "DELETE FROM comment WHERE id = "+id;
        Statement st = cnx.createStatement();

        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean delete(Comment comment) throws SQLException {
        String req = "DELETE FROM comment WHERE id = "+comment.getId();
        Statement st = cnx.createStatement();

        return st.executeUpdate(req) == -1;
    }

    @Override
    public List<Comment> selectAll() throws SQLException {
        List<Comment> comments = new ArrayList<Comment>();
        String req = "SELECT * FROM comment";
        Statement st = cnx.createStatement();
        ResultSet resultSet = st.executeQuery(req);
        while(resultSet.next()){
            Comment com = new Comment();
            com.setId(resultSet.getInt(1));
            com.setDescription(resultSet.getString(2));
            com.setUserId(resultSet.getInt(3));


            comments.add(com);
        }
        return comments;
    }

    @Override
    public List<Comment> selectWherePending() throws SQLException {
        return null;
    }
}
