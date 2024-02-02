package com.example.livrebook.service.deliveryServices;

import com.example.livrebook.model.delivery.Delivery;
import com.example.livrebook.model.book.Orders;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryService implements CRUD<Delivery> {
    private Connection cnx;

    public DeliveryService() {
        cnx = DbConnection.getInstance().getCnx();
    }
    @Override
    public boolean insert(Delivery delivery) throws SQLException {
        String req = "INSERT INTO livraison (id, status , adress, orderId) " +
                "VALUES ('" + delivery.getId() + "','" + "',status'=not delivred"
                + "','" + delivery.getAdress()+ "','" + delivery.getOrderId() +"','"+ delivery.isPending()+"')" ;


        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean update(Delivery delivery) throws SQLException {

            String req = "UPDATE livraison SET status = '"+ delivery.getStatus()+
                    "', adress = '"+ delivery.getAdress()+"', orderId = '"+ delivery.getOrderId()+"'"+
                    ", pending = 1 "+
                    "WHERE id = "+ delivery.getId();
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
    public boolean delete(Delivery delivery) throws SQLException {
        String req = "DELETE FROM livraison WHERE id=?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, delivery.getId());

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    @Override
    public List<Delivery> selectAll() throws SQLException {
            List<Delivery> deliveries = new ArrayList<>();
            List<Orders> orders = new ArrayList<>();
            String req = "SELECT * FROM livraison,orders WHERE livraison.orderId = orders.userId ";

            try (Statement st = cnx.createStatement();
                 ResultSet rs = st.executeQuery(req)) {

                while (rs.next()) {
                    Delivery delivery = new Delivery();
                    Orders order = new Orders();
                    delivery.setId(rs.getInt("id"));
                    delivery.setStatus(rs.getString("status"));
                    delivery.setAdress(rs.getString("adress"));
                    delivery.setOrderId(rs.getInt("orderId"));
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("userId"));
                    order.setOrderDate(rs.getDate("orderDate"));
                    order.setOrderStatus(rs.getString("orderStatus"));
                    order.setPaymentMethod(rs.getString("paymentMethod"));
                    order.setTotalPrice(rs.getInt("totalPrice"));
                    deliveries.add(delivery);
                    orders.add(order);
                }
            }

            return deliveries;

        }
    public List<Map<String, Object>> selectLivraisonByOrder() throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        String req = "SELECT * FROM livraison, orders WHERE livraison.orderId = orders.userId ";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();

                row.put("livraisonId", rs.getInt("livraison.id"));
                row.put("livraisonStatus", rs.getString("livraison.status"));
                row.put("livraisonAdress", rs.getString("livraison.adress"));
                row.put("orderId", rs.getInt("livraison.orderId"));

                row.put("ordersId", rs.getInt("orders.id"));
                row.put("userId", rs.getInt("orders.userId"));
                row.put("orderDate", rs.getDate("orders.orderDate"));
                row.put("orderStatus", rs.getString("orders.orderStatus"));
                row.put("paymentMethod", rs.getString("orders.paymentMethod"));
                row.put("totalPrice", rs.getInt("orders.totalPrice"));

                result.add(row);
            }
        }

        return result;
    }



    @Override
    public List<Delivery> selectWherePending() throws SQLException {
        List<Delivery> deliveries = new ArrayList<>();
        String req = "SELECT * FROM livraison WHERE pending =true AND status = 'not delivred' ";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Delivery delivery = new Delivery();
                delivery.setId(rs.getInt("id"));
                delivery.setStatus(rs.getString("status"));
                delivery.setAdress(rs.getString("adress"));
                delivery.setOrderId(rs.getInt("orderId"));
                delivery.setPending(rs.getBoolean("pending"));
                deliveries.add(delivery);
            }
        }

        return deliveries;
    }

    public List<Delivery> selectWhereStatus() throws SQLException {
        List<Delivery> deliveries = new ArrayList<>();
        String req = "SELECT * FROM livraison WHERE status ='delivred'";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Delivery delivery = new Delivery();
                delivery.setId(rs.getInt("id"));
                delivery.setStatus(rs.getString("status"));
                delivery.setAdress(rs.getString("adress"));
                delivery.setOrderId(rs.getInt("orderId"));
                delivery.setPending(rs.getBoolean("pending"));
                deliveries.add(delivery);
            }
        }

        return deliveries;
    }
}

