package com.example.livrebook.service.bookServices;

import com.example.livrebook.model.book.OrderLine;
import com.example.livrebook.model.user.User;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.SQLException;
import java.util.List;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

    public class OrderLineService implements CRUD<OrderLine> {
        private Connection cnx;

        public OrderLineService() {
            cnx = DbConnection.getInstance().getCnx();
        }

        @Override
        public boolean insert(OrderLine orderLine) throws SQLException {
            String req = "INSERT INTO orderline (idOrder, idUser, quantityBookId) " +
                    "VALUES (" + orderLine.getIdOrder() + ", " + orderLine.getIdUser() + ", " + orderLine.getQuantityBookId() + ")";

            try (Statement st = cnx.createStatement()) {
                return st.executeUpdate(req) == -1;
            }
        }

        @Override
        public boolean update(OrderLine orderLine) throws SQLException {
            String req = "UPDATE orderline SET idOrder = " + orderLine.getIdOrder() +
                    ", idUser = " + orderLine.getIdUser() +
                    ", quantityBookId = " + orderLine.getQuantityBookId() +
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
                    orderLine.setIdUser(resultSet.getInt("idUser"));
                    orderLine.setQuantityBookId(resultSet.getInt("quantityBookId"));

                    orderLines.add(orderLine);
                }
            }
            return orderLines;
        }
    }