package com.example.livrebook.service.bookServices;

import com.example.livrebook.model.book.BookQuantity;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

    public class BookQuantityService implements CRUD<BookQuantity> {
        private Connection cnx;

        public BookQuantityService() {
            cnx = DbConnection.getInstance().getCnx();        }

        @Override
        public boolean insert(BookQuantity bookQuantity) throws SQLException {
            String req = "INSERT INTO bookquantity (bookId, quantity) " +
                    "VALUES (" + bookQuantity.getBookId() + ", " + bookQuantity.getQuantity() + ")";

            try (Statement st = cnx.createStatement()) {
                return st.executeUpdate(req) == -1;
            }
        }

        @Override
        public boolean update(BookQuantity bookQuantity) throws SQLException {
            String req = "UPDATE bookquantity SET bookId = " + bookQuantity.getBookId() +
                    ", quantity = " + bookQuantity.getQuantity() +
                    " WHERE bookQuantityId = " + bookQuantity.getBookQuantityId();

            try (Statement st = cnx.createStatement()) {
                return st.executeUpdate(req) == -1;
            }
        }

        @Override
        public boolean delete(int id) throws SQLException {
            String req = "DELETE FROM bookquantity WHERE bookQuantityId = " + id;
            try (Statement st = cnx.createStatement()) {
                return st.executeUpdate(req) == -1;
            }
        }

        @Override
        public boolean delete(BookQuantity bookQuantity) throws SQLException {
            return delete(bookQuantity.getBookQuantityId());
        }

        @Override
        public List<BookQuantity> selectAll() throws SQLException {
            List<BookQuantity> bookQuantities = new ArrayList<>();
            String req = "SELECT * FROM bookquantity";
            try (Statement st = cnx.createStatement();
                 ResultSet resultSet = st.executeQuery(req)) {
                while (resultSet.next()) {
                    BookQuantity bookQuantity = new BookQuantity();
                    bookQuantity.setBookQuantityId(resultSet.getInt("bookQuantityId"));
                    bookQuantity.setBookId(resultSet.getInt("bookId"));
                    bookQuantity.setQuantity(resultSet.getInt("quantity"));

                    bookQuantities.add(bookQuantity);
                }
            }
            return bookQuantities;
        }

        @Override
        public List<BookQuantity> selectWherePending() throws SQLException {
            return null;
        }


    }