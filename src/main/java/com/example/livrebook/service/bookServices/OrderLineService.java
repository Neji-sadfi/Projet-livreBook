package com.example.livrebook.service.bookServices;

import com.example.livrebook.model.book.Book;
import com.example.livrebook.model.book.OrderLine;
import com.example.livrebook.model.book.OrderLineBook;
import com.example.livrebook.model.user.User;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

    public class OrderLineService implements CRUD<OrderLine> {
        private Connection cnx;
        private HashMap<Book, OrderLine> bookQuantity;
        public OrderLineService() {
            cnx = DbConnection.getInstance().getCnx();
        }

        @Override
        public boolean insert(OrderLine orderLine) throws SQLException {
            String req = "INSERT INTO orderline (idOrder, bookId, quantity) " +
                    "VALUES (" + orderLine.getIdOrder() + ", " + orderLine.getBookId() + ", " + orderLine.getQuantity() + ")";

            Statement st = cnx.createStatement();
                return st.executeUpdate(req) == -1;

        }

        @Override
        public boolean update(OrderLine orderLine) throws SQLException {
            String req = "UPDATE orderline SET idOrder = " + orderLine.getIdOrder() +
                    ", bookId = " + orderLine.getBookId() +
                    ", quantity = " + orderLine.getQuantity() +
                    " WHERE id = " + orderLine.getId();

            try (Statement st = cnx.createStatement()) {
                return st.executeUpdate(req) == -1;
            }
        }

        @Override
        public boolean delete(int id) throws SQLException {
            String req = "DELETE FROM orderline WHERE id = " + id;
            try (Statement st = cnx.createStatement()) {
                return st.executeUpdate(req) == -1;
            }
        }

        @Override
        public boolean delete(OrderLine orderLine) throws SQLException {
            return delete(orderLine.getId());
        }

        @Override
        public List<OrderLine> selectAll() throws SQLException {
            List<OrderLine> orderLines = new ArrayList<>();
            String req = "SELECT * FROM orderline";
            try (Statement st = cnx.createStatement();
                 ResultSet resultSet = st.executeQuery(req)) {
                while (resultSet.next()) {
                    OrderLine orderLine = new OrderLine();
                    orderLine.setId(resultSet.getInt("id"));
                    orderLine.setIdOrder(resultSet.getInt("idOrder"));
                    orderLine.setBookId(resultSet.getInt("bookId"));
                    orderLine.setQuantity(resultSet.getInt("quantity"));

                    orderLines.add(orderLine);
                }
            }
            return orderLines;
        }

        public List<OrderLineBook> getAllUserOrders(int userId) throws SQLException {
            List<OrderLineBook> userOrders = new ArrayList<>();

            String query = "SELECT o.id AS orderId, o.orderDate, o.orderStatus, o.paymentMethod, o.totalPrice, " +
                    "b.id AS bookId, b.title, b.author, b.category, b.language, b.price, b.picture, " +
                    "ol.id AS orderLineId, ol.quantity " +
                    "FROM orders o " +
                    "JOIN orderline ol ON o.id = ol.idOrder " +
                    "JOIN book b ON ol.bookId = b.id " +
                    "WHERE o.userId = ?";

            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int orderId = resultSet.getInt("orderId");
                        OrderLineBook orderLineBook = userOrders.stream()
                                .filter(order -> order.getOrderId() == orderId)
                                .findFirst()
                                .orElse(null);

                        if (orderLineBook == null) {
                            orderLineBook = new OrderLineBook();
                            orderLineBook.setOrderId(orderId);
                            orderLineBook.setOrderDate(resultSet.getDate("orderDate"));
                            orderLineBook.setOrderStatus(resultSet.getString("orderStatus"));
                            orderLineBook.setPaymentMethod(resultSet.getString("paymentMethod"));
                            orderLineBook.setTotalPrice(resultSet.getInt("totalPrice"));

                            userOrders.add(orderLineBook);
                        }

                        // Créez un objet Book
                        Book book = new Book();
                        book.setId(resultSet.getInt("bookId"));
                        book.setTitle(resultSet.getString("title"));
                        book.setAuthor(resultSet.getString("author"));
                        book.setCategory(resultSet.getString("category"));
                        book.setLanguage(resultSet.getString("language"));
                        book.setPrice(resultSet.getInt("price"));
                        book.setPicture(resultSet.getString("picture"));

                        // Créez un objet OrderLine
                        OrderLine orderLine = new OrderLine();
                        orderLine.setId(resultSet.getInt("orderLineId"));
                        orderLine.setQuantity(resultSet.getInt("quantity"));
                        orderLine.setBookId(book.getId());

                        // Ajoutez le livre et sa quantité à la HashMap dans OrderLineBook
                        orderLineBook.addBookQuantity(book, orderLine);
                    }
                }
            }

            return userOrders;
        }

    }