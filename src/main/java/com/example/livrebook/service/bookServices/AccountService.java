package com.example.livrebook.service.bookServices;

import com.example.livrebook.model.user.Account;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountService implements CRUD<Account> {
    private Connection cnx;

    public AccountService() {
        cnx = DbConnection.getInstance().getCnx();
    }

    public boolean insert(Account account) throws SQLException {
        String req = "INSERT INTO account (amount, accountNumber, code, userId) " +
                "VALUES (" + account.getAmount() + ", " + account.getAccountNumber() + ", " +
                account.getCode() + ", " + account.getUserId() + ")";

        try (Statement st = cnx.createStatement()) {
            return st.executeUpdate(req) == -1;
        }
    }

    public boolean update(Account account) throws SQLException {
        String req = "UPDATE account SET amount = " + account.getAmount() +
                ", accountNumber = " + account.getAccountNumber() +
                ", code = " + account.getCode() +
                ", userId = " + account.getUserId() +
                " WHERE id = " + account.getId();

        try (Statement st = cnx.createStatement()) {
            return st.executeUpdate(req) == -1;
        }
    }

    public boolean delete(int id) throws SQLException {
        String req = "DELETE FROM account WHERE id = " + id;
        try (Statement st = cnx.createStatement()) {
            return st.executeUpdate(req) == -1;
        }
    }

    public boolean delete(Account account) throws SQLException {
        return delete(account.getId());
    }
    public Account getAccountByUserId(int userId) throws SQLException {
        String req = "SELECT * FROM account WHERE userId =" + userId;

        try (Statement st = cnx.createStatement();
             ResultSet resultSet = st.executeQuery(req)) {

            if (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("id"));
                account.setAmount(resultSet.getInt("amount"));
                account.setAccountNumber(resultSet.getInt("accountNumber"));
                account.setCode(resultSet.getInt("code"));
                account.setUserId(resultSet.getInt("userId"));
                return account;
            } else {
                // If no results found for the given userId, return null or handle accordingly
                return null;
            }
        }
    }


    public List<Account> selectAll() throws SQLException {
        List<Account> accountList = new ArrayList<>();
        String req = "SELECT * FROM account";
        try (Statement st = cnx.createStatement();
             ResultSet resultSet = st.executeQuery(req)) {
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("id"));
                account.setAmount(resultSet.getInt("amount"));
                account.setAccountNumber(resultSet.getInt("accountNumber"));
                account.setCode(resultSet.getInt("code"));
                account.setUserId(resultSet.getInt("userId"));

                accountList.add(account);
            }
        }
        return accountList;
    }
}
