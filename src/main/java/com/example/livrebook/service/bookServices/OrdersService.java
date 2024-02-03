package com.example.livrebook.service.bookServices;

import com.example.livrebook.model.book.Orders;
import com.example.livrebook.model.user.User;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
            String req = "UPDATE orders SET orderStatus = '" + order.getOrderStatus() +
                    "' WHERE id = " + order.getId();

           Statement st = cnx.createStatement();
                return st.executeUpdate(req) == 1;

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
                    order.setTotalPrice(resultSet.getInt("bookQuantityId"));
                    ordersList.add(order);
                }
            }
            return ordersList;
        }

        @Override
        public List<Orders> selectWherePending() throws SQLException {
            return null;
        }

        public int insertAndGetGeneratedKey(Orders order) throws SQLException {
            String req = "INSERT INTO orders (userId, orderDate, orderStatus, paymentMethod, totalPrice) " +
                    "VALUES (" + order.getUserId() + ", '" + new java.sql.Date(order.getOrderDate().getTime()) + "', '" +
                    order.getOrderStatus() + "', '" + order.getPaymentMethod() + "', " + order.getTotalPrice() + ")";


            try (Statement st = cnx.createStatement()) {
                st.executeUpdate(req, Statement.RETURN_GENERATED_KEYS);
                ResultSet resultSet = st.getGeneratedKeys();

                if (resultSet.next()) {
                    return resultSet.getInt(1); // Retrieve the generated key
                } else {
                    throw new SQLException("Insertion failed, no ID obtained.");
                }
            }
        }
        public HashMap<Orders, User> getOrdersAndUsers() {
            HashMap<Orders, User> ordersAndUsers = new HashMap<>();

            String sql = "SELECT o.*, u.* FROM orders o INNER JOIN user u ON o.userId = u.id";

            try (PreparedStatement statement = cnx.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Orders order = extractOrderFromResultSet(resultSet);
                    User user = extractUserFromResultSet(resultSet);
                    ordersAndUsers.put(order, user);
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Gérer les exceptions correctement dans votre application
            }

            return ordersAndUsers;
        }
        private Orders extractOrderFromResultSet(ResultSet resultSet) throws SQLException {
            Orders order = new Orders();
            order.setId(resultSet.getInt("id"));
            order.setUserId(resultSet.getInt("userId"));
            order.setOrderDate(resultSet.getDate("orderDate"));
            order.setOrderStatus(resultSet.getString("orderStatus"));
            order.setPaymentMethod(resultSet.getString("paymentMethod"));
            order.setTotalPrice(resultSet.getInt("totalPrice"));
            // Remplir les autres champs de la commande
            return order;
        }
        private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setFirst_name(resultSet.getString("first_name"));
            user.setLast_name(resultSet.getString("last_name"));
            user.setEmail(resultSet.getString("email"));
            user.setBirthDate(resultSet.getDate("birthDate"));
            user.setGender(resultSet.getString("gender"));
            user.setPhoneNumber(resultSet.getString("phoneNumber"));
            user.setPassword(resultSet.getString("password"));
            user.setVerified(resultSet.getBoolean("isVerified"));
            user.setStatus(resultSet.getString("status"));
            // Ajouter les autres champs de l'utilisateur
            return user;
        }
        public HashMap<Orders, User> searchOrders(String userName, LocalDate orderDate, String userEmail, String paymentMethod) {
            HashMap<Orders, User> ordersAndUsers = new HashMap<>();

            String query = "SELECT * FROM orders o " +
                    "INNER JOIN user u ON o.userId = u.id " +
                    "WHERE 1=1 " +
                    (userName != null && !userName.isEmpty() ? "AND (u.first_name LIKE ? OR u.last_name LIKE ?) " : "") +
                    (orderDate != null ? "AND o.orderDate = ? " : "") +
                    (userEmail != null && !userEmail.isEmpty() ? "AND u.email LIKE ? " : "") +
                    (paymentMethod != null && !paymentMethod.isEmpty() ? "AND o.paymentMethod LIKE ? " : "");

            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                int parameterIndex = 1;

                if (userName != null && !userName.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, "%" + userName + "%");
                    preparedStatement.setString(parameterIndex++, "%" + userName + "%");
                }

                if (orderDate != null) {
                    preparedStatement.setObject(parameterIndex++, java.sql.Date.valueOf(orderDate));
                }

                if (userEmail != null && !userEmail.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, "%" + userEmail + "%");
                }

                if (paymentMethod != null && !paymentMethod.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, "%" + paymentMethod + "%");
                }

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Orders order = extractOrderFromResultSet(resultSet);
                        User user = extractUserFromResultSet(resultSet);
                        ordersAndUsers.put(order, user);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return ordersAndUsers;
        }

    }

