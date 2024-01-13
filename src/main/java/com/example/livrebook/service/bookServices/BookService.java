package com.example.livrebook.service.bookServices;

import com.example.livrebook.model.book.Book;
import com.example.livrebook.model.user.User;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookService implements CRUD<Book> {
    private Connection cnx;

    public BookService(){
        cnx = DbConnection.getInstance().getCnx();
    }

    @Override
    public boolean insert(Book book) throws SQLException {
        String req = "INSERT INTO book (title, author, category, language, nbPages, quantity, price, summary, datePublication, picture) " +
                "VALUES ('" + book.getTitle() + "','" + book.getAuthor() + "','" + book.getCategory() + "','" +
                book.getLanguage() + "'," + book.getNbPages() + "," + book.getQuantity() + "," +
                book.getPrice() + ",'" + book.getSummary() + "','" +
                new java.sql.Date(book.getDatePublication().getTime()) + "','" + book.getPicture() + "')";

        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean update(Book book) throws SQLException {
        String req = "UPDATE book SET title = '" + book.getTitle() +
                "', author = '" + book.getAuthor() + "', category = '" + book.getCategory() +
                "', language = '" + book.getLanguage() + "', nbPages = " + book.getNbPages() +
                ", quantity = " + book.getQuantity() + ", price = " + book.getPrice() +
                ", summary = '" + book.getSummary() + "', datePublication = '" +
                new java.sql.Date(book.getDatePublication().getTime()) + "', picture = '" +
                book.getPicture() + "' WHERE id = " + book.getId();

        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String req = "DELETE FROM book WHERE id = " + id;
        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean delete(Book book) throws SQLException {
        return delete(book.getId());
    }

    @Override
    public List<Book> selectAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        String req = "SELECT * FROM book";
        Statement st = cnx.createStatement();
        ResultSet resultSet = st.executeQuery(req);

        while (resultSet.next()) {
            Book book = new Book();
            book.setId(resultSet.getInt("id"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(resultSet.getString("author"));
            book.setCategory(resultSet.getString("category"));
            book.setLanguage(resultSet.getString("language"));
            book.setNbPages(resultSet.getInt("nbPages"));
            book.setQuantity(resultSet.getInt("quantity"));
            book.setPrice(resultSet.getInt("price"));
            book.setSummary(resultSet.getString("summary"));
            book.setDatePublication(resultSet.getDate("datePublication"));
            book.setPicture(resultSet.getString("picture"));

            books.add(book);
        }

        return books;
    }

}
