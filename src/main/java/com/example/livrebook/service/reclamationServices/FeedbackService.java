package com.example.livrebook.service.reclamationServices;

import com.example.livrebook.model.reclamation.FeedBack;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackService implements CRUD<FeedBack> {
    private Connection cnx;

    public FeedbackService() {
        cnx = DbConnection.getInstance().getCnx();
    }

    @Override
    public boolean insert(FeedBack feedback) throws SQLException {
        String req = "INSERT INTO feedback (nom, email, description) VALUES (?, ?, ?)";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, feedback.getNom());
            st.setString(2, feedback.getEmail());
            st.setString(3, feedback.getDescription());

            return st.executeUpdate() == 1;
        }
    }


    @Override
    public boolean update(FeedBack feedback) throws SQLException {
        String req = "UPDATE feedback SET nom = ?, email = ?, description = ? " +
                "WHERE id = ?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, feedback.getNom());
            st.setString(2, feedback.getEmail());
            st.setString(3, feedback.getDescription());
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
                feedback.setNom(resultSet.getString("nom"));
                feedback.setEmail(resultSet.getString("email"));
                feedback.setDescription(resultSet.getString("description"));

                feedbacks.add(feedback);
            }
        }
        return feedbacks;
    }

    public void removeFeedback(FeedBack feedback) throws SQLException {
        String sql = "DELETE FROM feedback WHERE id = ?"; // Supposons que 'id' est la clé primaire de votre table feedback

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, feedback.getId()); // Supposons que vous avez une méthode getId() dans la classe FeedBack

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                // Gérer le cas où aucune ligne n'est supprimée (feedback inexistant)
                System.out.println("Aucune réclamation avec cet ID n'a été trouvée.");
            } else {
                // Gérer le cas où la suppression a réussi
                System.out.println("Réclamation supprimée avec succès.");
            }
        }
    }
}
