package com.example.livrebook.service.livraisonServices;

import com.example.livrebook.model.livraison.Livraison;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivraisonService implements CRUD<Livraison> {
    private Connection cnx;

    public LivraisonService() {
        cnx = DbConnection.getInstance().getCnx();
    }
    @Override
    public boolean insert(Livraison livraison) throws SQLException {
        String req = "INSERT INTO livraison (id, status , adress, orderId) " +
                "VALUES ('" + livraison.getId() + "','" + livraison.getStatus()
                + "','" + livraison.getAdress()+ "','" + livraison.getOrderId() + "')" ;


        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean update(Livraison livraison) throws SQLException {

            String req = "UPDATE livraison SET status = '"+livraison.getStatus()+
                    "', adress = '"+ livraison.getAdress()+"', orderId = '"+livraison.getOrderId()+"'"+
                    "WHERE id = "+ livraison.getId();
            Statement st = cnx.createStatement();
            return st.executeUpdate(req) == -1;
        }

    @Override
    public boolean delete(int id) throws SQLException {
            String req = "DELETE FROM livraison WHERE id=?";

            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setInt(1, id);

                return st.executeUpdate() == 1;
            }
    }

    @Override
    public boolean delete(Livraison livraison) throws SQLException {
        String req = "DELETE FROM livraison WHERE id=?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, livraison.getId());

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    @Override
    public List<Livraison> selectAll() throws SQLException {
            List<Livraison> livraisons = new ArrayList<>();
            String req = "SELECT id, status, adress, orderId FROM livraison";

            try (PreparedStatement st = cnx.prepareStatement(req);
                 ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    Livraison livraison = new Livraison();
                    livraison.setId(rs.getInt("id"));
                    livraison.setStatus(rs.getString("status"));
                    livraison.setAdress(rs.getString("adress"));
                    livraison.setOrderId(rs.getInt("orderId"));
                    livraisons.add(livraison);
                }
            }

            return livraisons;
        }
    }

