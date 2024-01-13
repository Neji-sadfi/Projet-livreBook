package com.example.livrebook.service.bookServices;

import com.example.livrebook.model.book.Orders;
import com.example.livrebook.model.user.User;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

    public class OrdersService implements CRUD<Orders> {
        private Connection cnx;

        public OrdersService(){
            cnx = DbConnection.getInstance().getCnx();
        }

        public boolean insert(Orders order) throws SQLException {
            String req = "INSERT INTO orders (userId, orderDate, orderStatus, paymentMethod, totalPrice) " +
                    "VALUES (" + order.getUserId() + ", '" + new java.sql.Date(order.getOrderDate().getTime()) + "', '" +
                    order.getOrderStatus() + "', '" + order.getPaymentMethod() + "', " + order.getTotalPrice() + ")";

            try (Statement st = cnx.createStatement()) {
                return st.executeUpdate(req) == -1;
            }
        }

        public boolean update(Orders order) throws SQLException {
            String req = "UPDATE orders SET userId = " + order.getUserId() +
                    ", orderDate = '" + new java.sql.Date(order.getOrderDate().getTime()) +
                    "', orderStatus = '" + order.getOrderStatus() +
                    "', paymentMethod = '" + order.getPaymentMethod() +
                    "', totalPrice = " + order.getTotalPrice() +
                    " WHERE id = " + order.getId();

            try (Statement st = cnx.createStatement()) {
                return st.executeUpdate(req) == -1;
            }
        }

        public boolean delete(int id) throws SQLException {
            String req = "DELETE FROM orders WHERE id = " + id;
            try (Statement st = cnx.createStatement()) {
                return st.executeUpdate(req) == -1;
            }
        }

        public boolean delete(Orders order) throws SQLException {
            return delete(order.getId());
        }

        public List<Orders> selectAll() throws SQLException {
            List<Orders> ordersList = new ArrayList<>();
            String req = "SELECT * FROM orders";
            try (Statement st = cnx.createStatement();
                 ResultSet resultSet = st.executeQuery(req)) {
                while (resultSet.next()) {
                    Orders order = new Orders();
                    order.setId(resultSet.getInt("id"));
                    order.setUserId(resultSet.getInt("userId"));
                    order.setOrderDate(resultSet.getDate("orderDate"));
                    order.setOrderStatus(resultSet.getString("orderStatus"));
                    order.setPaymentMethod(resultSet.getString("paymentMethod"));
                    order.setTotalPrice(resultSet.getInt("totalPrice"));

                    ordersList.add(order);
                }
            }
            return ordersList;
        }

    }
