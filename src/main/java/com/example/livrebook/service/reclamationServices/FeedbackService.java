package com.example.livrebook.service.reclamationServices;

import com.example.livrebook.model.reclamation.FeedBack;

import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackService implements CRUD<FeedBack> {
    private Connection cnx;

    public FeedbackService() {
        cnx = DbConnection.getInstance().getCnx();
    }

    @Override
    public boolean insert(FeedBack feedback) throws SQLException {
        String req = "INSERT INTO feedback (message, userId, bookId) " +
                "VALUES ('" + feedback.getMessage() + "','" + feedback.getUserId() + "','" + feedback.getBookId() + "')";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, feedback.getMessage());
            st.setInt(2, feedback.getUserId());
            st.setInt(3, feedback.getBookId());

            return st.executeUpdate() == 1;
        }
    }

    @Override
    public boolean update(FeedBack feedback) throws SQLException {
        String req = "UPDATE feedback SET message = ?, userId = ?, bookId = ? " +
                "WHERE id = ?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, feedback.getMessage());
            st.setInt(2, feedback.getUserId());
            st.setInt(3, feedback.getBookId());
            st.setInt(4, feedback.getId());

            return st.executeUpdate() == 1;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String req = "DELETE FROM feedback WHERE id = ?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, id);

            return st.executeUpdate() == 1;
        }
    }

    @Override
    public boolean delete(FeedBack feedback) throws SQLException {
        return delete(feedback.getId());
    }

    @Override
    public List<FeedBack> selectAll() throws SQLException {
        List<FeedBack> feedbacks = new ArrayList<>();
        String req = "SELECT * FROM feedback";

        try (PreparedStatement st = cnx.prepareStatement(req);
             ResultSet resultSet = st.executeQuery()) {

            while (resultSet.next()) {
                FeedBack feedback = new FeedBack();
                feedback.setId(resultSet.getInt("id"));
                feedback.setMessage(resultSet.getString("message"));
                feedback.setUserId(resultSet.getInt("userId"));
                feedback.setBookId(resultSet.getInt("bookId"));

                feedbacks.add(feedback);
            }
        }
        return feedbacks;
    }
}
