package com.example.livrebook.service.bookServices;

import com.example.livrebook.model.book.Book;
import com.example.livrebook.model.user.User;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.*;
import java.time.LocalDate;
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
    public Book selectById(int id) throws SQLException {
        String req = "SELECT * FROM book WHERE id ="+id;
        Statement st = cnx.createStatement();
        ResultSet resultSet = st.executeQuery(req);
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


        return book;
    }
    public List<Book> searchBooks(String title, String author, String category, String language, LocalDate date, Integer price) throws SQLException {
        // La requête SQL paramétrée
        String query = "SELECT * FROM book WHERE " +
                "(? IS NULL OR title LIKE ?) AND " +
                "(? IS NULL OR author LIKE ?) AND " +
                "(? IS NULL OR category LIKE ?) AND " +
                "(? IS NULL OR language LIKE ?) AND " +
                "(? IS NULL OR datePublication = ?) AND " +
                "(? IS NULL OR price = ?)";



        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            // Paramétrage des valeurs
            statement.setObject(1, (title != null) ? "%" + title + "%" : null);
            statement.setObject(2, (title != null) ? "%" + title + "%" : null);
            statement.setObject(3, (author != null) ? "%" + author + "%" : null);
            statement.setObject(4, (author != null) ? "%" + author + "%" : null);
            statement.setObject(5, (category != null) ? "%" + category + "%" : null);
            statement.setObject(6, (category != null) ? "%" + category + "%" : null);
            statement.setObject(7, (language != null) ? "%" + language + "%" : null);
            statement.setObject(8, (language != null) ? "%" + language + "%" : null);
            statement.setObject(9, (date != null) ? Date.valueOf(date) : null);
            statement.setObject(10, (date != null) ? Date.valueOf(date) : null);
            statement.setObject(11, (price != null) ? price : null);
            statement.setObject(12, (price != null) ? price : null);




            // Exécution de la requête
            try (ResultSet resultSet = statement.executeQuery()) {

                List<Book> books = new ArrayList<>();
                while (resultSet.next()) {
                    Book book = new Book();
                    // Initialisez les propriétés du livre à partir du résultat de la requête
                    book.setId(resultSet.getInt("id"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setCategory(resultSet.getString("category"));
                    book.setDatePublication(resultSet.getDate("datePublication"));
                    book.setLanguage(resultSet.getString("language"));
                    book.setPrice(resultSet.getInt("price"));
                    book.setQuantity(resultSet.getInt("quantity"));
                    book.setSummary(resultSet.getString("summary"));
                    book.setTitle(resultSet.getString("title"));
                    book.setPicture(resultSet.getString("picture"));

                    books.add(book);
                }

                return books;
            }
        }
    }
    public List<Book> searchBooksClient(String title, String category, String language) throws SQLException {
        // The parameterized SQL query with OR conditions
        String query = "SELECT * FROM book WHERE " +
                "(? IS NULL OR title LIKE ?) AND " +
                "(? IS NULL OR category LIKE ?) AND " +
                "(? IS NULL OR language LIKE ?)";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            // Set parameters for the query
            statement.setObject(1, (title != null) ? "%" + title + "%" : null);
            statement.setObject(2, (title != null) ? "%" + title + "%" : null);
            statement.setObject(3, (category != null) ? "%" + category + "%" : null);
            statement.setObject(4, (category != null) ? "%" + category + "%" : null);
            statement.setObject(5, (language != null) ? "%" + language + "%" : null);
            statement.setObject(6, (language != null) ? "%" + language + "%" : null);

            // Execute the query
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Book> books = new ArrayList<>();
                while (resultSet.next()) {
                    Book book = new Book();
                    // Initialize book properties from the query result
                    book.setId(resultSet.getInt("id"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setCategory(resultSet.getString("category"));
                    book.setDatePublication(resultSet.getDate("datePublication"));
                    book.setLanguage(resultSet.getString("language"));
                    book.setPrice(resultSet.getInt("price"));
                    book.setQuantity(resultSet.getInt("quantity"));
                    book.setSummary(resultSet.getString("summary"));
                    book.setTitle(resultSet.getString("title"));
                    book.setPicture(resultSet.getString("picture"));

                    books.add(book);
                }
                System.out.println(books);
                return books;
            }
        }
    }
}
