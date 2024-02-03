package com.example.livrebook.service.userServices;

import com.example.livrebook.model.user.User;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements CRUD<User> {
    private Connection cnx;

    public UserService(){
        cnx = DbConnection.getInstance().getCnx();
    }

    private boolean isValidRole(String role) {
        return "Admin".equalsIgnoreCase(role) || "Client".equalsIgnoreCase(role);
    }


    @Override
    public boolean insert(User user) throws SQLException {
        if (user.getRole() == null || !isValidRole(user.getRole())) {
            user.setRole("Client");
        }

        String req = "INSERT INTO user (user.first_name, user.last_name, user.email, user.gender, user.phoneNumber, user.password, user.confirmpassword, user.question, user.answer, user.role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, user.getFirst_name());
            preparedStatement.setString(2, user.getLast_name());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getGender());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setString(7, user.getConfirmpassword());
            preparedStatement.setString(8, user.getQuestion());
            preparedStatement.setString(9, user.getAnswer());
            preparedStatement.setString(10, user.getRole());

            return preparedStatement.executeUpdate() > 0;
        }
    }


    public boolean login(User user) throws SQLException {
        String selectData = "SELECT * FROM user WHERE first_name=? AND password=?";
        try (PreparedStatement prepare = cnx.prepareStatement(selectData)) {
            prepare.setString(1, user.getFirst_name());
            prepare.setString(2, user.getPassword());

            try (ResultSet result = prepare.executeQuery()) {
                return result.next();
            }
        }
    }

    public boolean forgotPassword(User user) throws SQLException {
        String selectData = "SELECT * FROM user WHERE first_name=? AND question=? AND answer=?";
        try (PreparedStatement prepare = cnx.prepareStatement(selectData)) {
            prepare.setString(1, user.getFirst_name());
            prepare.setString(2, user.getQuestion());
            prepare.setString(3, user.getAnswer());

            try (ResultSet result = prepare.executeQuery()) {
                return result.next();
            }
        }
    }

    public User selectById(int userId) throws SQLException {
        User user = null;
        String query = "SELECT * FROM user WHERE id = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setFirst_name(resultSet.getString("first_name"));
                    user.setLast_name(resultSet.getString("last_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setGender(resultSet.getString("gender"));
                    user.setPhoneNumber(resultSet.getString("phoneNumber"));
                    user.setPassword(resultSet.getString("password"));
                    user.setConfirmpassword(resultSet.getString("confirmpassword"));
                }
            }
        }

        return user;
    }


    @Override
    public boolean update(User user) throws SQLException {
        String req = "UPDATE user SET first_name = '"+user.getFirst_name()+
                "', last_name = '"+ user.getLast_name()+"', email = '"+user.getEmail()+"'"+
                "WHERE id = "+ user.getId();
        Statement st = cnx.createStatement();
        return st.executeUpdate(req) == -1;
    }

    public boolean updatePassword(User user) throws SQLException {
        String req = "UPDATE user SET password = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getId());

            return preparedStatement.executeUpdate() > 0;
        }
    }


    @Override
    public boolean delete(int id) throws SQLException {
        String req = "DELETE FROM user WHERE id = "+id;
        Statement st = cnx.createStatement();

        return st.executeUpdate(req) == -1;
    }

    @Override
    public boolean delete(User user) throws SQLException {
        String req = "DELETE FROM user WHERE id = "+user.getId();
        Statement st = cnx.createStatement();

        return st.executeUpdate(req) == -1;
    }

    @Override
    public List<User> selectAll() throws SQLException {
        List<User> users = new ArrayList<User>();
        String req = "SELECT * FROM user";
        Statement st = cnx.createStatement();
        ResultSet resultSet = st.executeQuery(req);
        while(resultSet.next()){
            User u = new User();
            u.setId(resultSet.getInt(1));
            u.setFirst_name(resultSet.getString(2));
            u.setLast_name(resultSet.getString(3));
            u.setEmail(resultSet.getString(4));
            u.setGender(resultSet.getString(5));
            u.setPhoneNumber(resultSet.getString(6));
            u.setRole(resultSet.getString(11));
            u.setPassword(resultSet.getString(7));
            u.setConfirmpassword(resultSet.getString(8));

            users.add(u);
        }
        return users;
    }

    @Override
    public List<User> selectWherePending() throws SQLException {
        return null;
    }


}
