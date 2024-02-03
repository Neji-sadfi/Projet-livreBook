package com.example.livrebook.service.bookServices;

import com.example.livrebook.model.book.Book;
import com.example.livrebook.model.book.BookQuantity;
import com.example.livrebook.model.book.BookQuantityDetails;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookQuantityService implements CRUD<BookQuantity> {
        private Connection cnx;

        public BookQuantityService() {
            cnx = DbConnection.getInstance().getCnx();        }

        @Override
        public boolean insert(BookQuantity bookQuantity) throws SQLException {
            String req = "INSERT INTO bookquantity (bookId, bookQuantity) " +
                    "VALUES (" + bookQuantity.getBookId() + ", " + bookQuantity.getbookQuantity() + ")";

            try (Statement st = cnx.createStatement()) {
                return st.executeUpdate(req) == -1;
            }
        }

        @Override
        public boolean update(BookQuantity bookQuantity) throws SQLException {
            String req = "UPDATE bookquantity SET bookId = " + bookQuantity.getBookId() +
                    ", bookQuantity = " + bookQuantity.getbookQuantity() +
                    " WHERE bookQuantityId = " + bookQuantity.getBookQuantityId();

            try (Statement st = cnx.createStatement()) {
                return st.executeUpdate(req) == -1;
            }
        }
    public void deleteAll() throws SQLException {
        String deleteAllQuery = "DELETE FROM bookquantity";

        try (Statement statement = cnx.createStatement()) {
            statement.executeUpdate(deleteAllQuery);
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
                    bookQuantity.setbookQuantity(resultSet.getInt("bookQuantity"));

                    bookQuantities.add(bookQuantity);
                }
            }
            return bookQuantities;
        }

    public List<BookQuantityDetails> getAllBooksQuantities() throws SQLException {
        List<BookQuantityDetails> bookQuantities = new ArrayList<>();
        String req = "SELECT * FROM bookquantity q, book b where q.bookId = b.id";
        try (Statement st = cnx.createStatement();
             ResultSet resultSet = st.executeQuery(req)) {
            while (resultSet.next()) {
                BookQuantityDetails bookQuantityDetails = new BookQuantityDetails();
                bookQuantityDetails.setBookQuantityId(resultSet.getInt("bookQuantityId"));
                bookQuantityDetails.setBookId(resultSet.getInt("bookId"));
                bookQuantityDetails.setBookQuantity(resultSet.getInt("bookQuantity"));

                bookQuantityDetails.setId(resultSet.getInt("id"));
                bookQuantityDetails.setTitle(resultSet.getString("title"));
                bookQuantityDetails.setAuthor(resultSet.getString("author"));
                bookQuantityDetails.setCategory(resultSet.getString("category"));
                bookQuantityDetails.setLanguage(resultSet.getString("language"));
                bookQuantityDetails.setNbPages(resultSet.getInt("nbPages"));
                bookQuantityDetails.setQuantity(resultSet.getInt("quantity"));
                bookQuantityDetails.setPrice(resultSet.getInt("price"));
                bookQuantityDetails.setSummary(resultSet.getString("summary"));
                bookQuantityDetails.setDatePublication(resultSet.getDate("datePublication"));
                bookQuantityDetails.setPicture(resultSet.getString("picture"));

                bookQuantities.add(bookQuantityDetails);
            }
        }
        return bookQuantities;
    }

}