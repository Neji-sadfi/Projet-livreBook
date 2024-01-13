package com.example.livrebook.service;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {

    boolean insert(T t) throws SQLException;

    boolean update(T t) throws SQLException;

    boolean delete(int id) throws SQLException;

    boolean delete(T t) throws SQLException;

    List<T> selectAll() throws SQLException;
}
