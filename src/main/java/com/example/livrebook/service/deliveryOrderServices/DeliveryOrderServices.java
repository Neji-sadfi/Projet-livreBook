package com.example.livrebook.service.deliveryOrderServices;

import com.example.livrebook.model.book.Orders;
import com.example.livrebook.model.delivery.Delivery;
import com.example.livrebook.model.deliveryOrder.DeliveryOrder;
//import com.example.livrebook.model.deliveryOrder.DeliveryOrder;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryOrderServices implements CRUD<DeliveryOrder>{

    private Connection cnx;

    public DeliveryOrderServices() {
        cnx = DbConnection.getInstance().getCnx();
    }


    @Override
    public boolean insert(DeliveryOrder deliveryOrder) throws SQLException {
       return false;
    }

    @Override
    public boolean update(DeliveryOrder deliveryOrder) throws SQLException {
        String req = "UPDATE livraison SET status = '"+ deliveryOrder.getStatus()+
                "', adress = '"+ deliveryOrder.getAdress()+"', orderId = '"+ deliveryOrder.getOrderId()+"'"+
                ", pending = 0 "+
                "WHERE id = "+ deliveryOrder.getIdDelivery();
        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String req = "DELETE FROM livraison, orders WHERE livraison.id=? AND orders.id=?";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, id);

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted > 0;
        }
    }
    public boolean deleteDeliveryOrder(int livraisonId, int ordersId) throws SQLException {
        // Delete from livraison table
        String livraisonDeleteQuery = "DELETE FROM livraison WHERE id=?";
        try (PreparedStatement livraisonSt = cnx.prepareStatement(livraisonDeleteQuery)) {
            livraisonSt.setInt(1, livraisonId);
            int livraisonRowsDeleted = livraisonSt.executeUpdate();
            // You can check livraisonRowsDeleted if needed
        }

        // Delete from orders table
        String ordersDeleteQuery = "DELETE FROM orders WHERE id=?";
        try (PreparedStatement ordersSt = cnx.prepareStatement(ordersDeleteQuery)) {
            ordersSt.setInt(1, ordersId);
            int ordersRowsDeleted = ordersSt.executeUpdate();
            // You can check ordersRowsDeleted if needed
        }

        // Return true if at least one row was deleted in either table
        return true;
    }


    @Override
    public boolean delete(DeliveryOrder deliveryOrder) throws SQLException {
        String req = "DELETE FROM livraison,orders WHERE livraison.id AND =orders.id";

        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, deliveryOrder.getIdDelivery());

            int rowsDeleted = st.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    public List<DeliveryOrder> selectAll() throws SQLException {
        List<DeliveryOrder> deliverieOrder = new ArrayList<>();
        String req = "SELECT * FROM livraison l,orders o, user u WHERE l.orderId = o.id AND o.userId=u.id";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                DeliveryOrder deliveryOrder = new DeliveryOrder();
//                Orders order = new Orders();
                deliveryOrder.setIdDelivery(rs.getInt("id"));
                deliveryOrder.setStatus(rs.getString("status"));
                deliveryOrder.setAdress(rs.getString("adress"));
                deliveryOrder.setOrderId(rs.getInt("orderId"));
                deliveryOrder.setIdOrder(rs.getInt("id"));
                deliveryOrder.setUserId(rs.getInt("userId"));
                deliveryOrder.setOrderDate(rs.getDate("orderDate"));
                deliveryOrder.setOrderStatus(rs.getString("orderStatus"));
                deliveryOrder.setPaymentMethod(rs.getString("paymentMethod"));
                deliveryOrder.setTotalPrice(rs.getInt("totalPrice"));
                deliveryOrder.setFirst_name(rs.getString("first_name"));
                deliverieOrder.add(deliveryOrder);
            }
        }

        return deliverieOrder;

    }

    @Override
    public List selectWherePending() throws SQLException {
        List<DeliveryOrder> deliveries = new ArrayList<>();
        String req = "SELECT * FROM livraison l,orders o,user u WHERE pending =true AND l.status ='not delivred'AND l.orderId = o.id AND o.userId=u.id";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                DeliveryOrder deliveryOrder = new DeliveryOrder();
//                delivery.setIdDelivery(rs.getInt("id"));
                deliveryOrder.setIdDelivery(rs.getInt("id"));
                deliveryOrder.setStatus(rs.getString("status"));
                deliveryOrder.setAdress(rs.getString("adress"));
                deliveryOrder.setOrderId(rs.getInt("orderId"));
                deliveryOrder.setIdOrder(rs.getInt("id"));
                deliveryOrder.setUserId(rs.getInt("userId"));
                deliveryOrder.setOrderDate(rs.getDate("orderDate"));
                deliveryOrder.setOrderStatus(rs.getString("orderStatus"));
                deliveryOrder.setPaymentMethod(rs.getString("paymentMethod"));
                deliveryOrder.setTotalPrice(rs.getInt("totalPrice"));
                deliveryOrder.setFirst_name(rs.getString("first_name"));
                deliveries.add(deliveryOrder);
            }
        }

        return deliveries;
    }
    public List<DeliveryOrder> selectWhereStatus() throws SQLException {
        List<DeliveryOrder> deliveries = new ArrayList<>();
        String req = "SELECT * FROM livraison l,orders o,user u WHERE l.status ='delivred'AND l.orderId = o.id AND o.userId=u.id";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                DeliveryOrder deliveryOrder = new DeliveryOrder();
//                delivery.setIdOrder(rs.getInt("id"));
                deliveryOrder.setIdDelivery(rs.getInt("id"));
                deliveryOrder.setStatus(rs.getString("status"));
                deliveryOrder.setAdress(rs.getString("adress"));
                deliveryOrder.setOrderId(rs.getInt("orderId"));
                deliveryOrder.setIdOrder(rs.getInt("id"));
                deliveryOrder.setUserId(rs.getInt("userId"));
                deliveryOrder.setOrderDate(rs.getDate("orderDate"));
                deliveryOrder.setOrderStatus(rs.getString("orderStatus"));
                deliveryOrder.setPaymentMethod(rs.getString("paymentMethod"));
                deliveryOrder.setTotalPrice(rs.getInt("totalPrice"));
                deliveryOrder.setFirst_name(rs.getString("first_name"));
                deliveries.add(deliveryOrder);
            }
        }

        return deliveries;
    }

}
