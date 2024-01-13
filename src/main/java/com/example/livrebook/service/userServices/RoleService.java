package com.example.livrebook.service.userServices;

import com.example.livrebook.model.user.Role;
import com.example.livrebook.model.user.RoleName;
import com.example.livrebook.service.CRUD;
import com.example.livrebook.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class RoleService implements CRUD<Role> {
    private Connection cnx;

    public RoleService() {
        cnx = DbConnection.getInstance().getCnx();
    }
    @Override
    public boolean insert(Role role) throws SQLException {
        String sql = "INSERT INTO roles (name, description, status)" +
                "VALUES ('" + role.getName() + "','" + role.getDescription() + "','" + role.getStatus() + "')";

        try (PreparedStatement rs = cnx.prepareStatement(sql)) {
            rs.setString(1, role.getName().toString()); // Assuming RoleName is an enum
            rs.setString(2, role.getDescription());
            rs.setString(3, role.getStatus());

            return rs.executeUpdate()==1 ;
        }
    }

    @Override
    public boolean update(Role role) throws SQLException {

            String sql = "UPDATE roles SET name=?, description=?, status=? WHERE id=?";

            try (PreparedStatement rs = cnx.prepareStatement(sql)) {
                rs.setString(1, role.getName().toString()); // Assuming RoleName is an enum
                rs.setString(2, role.getDescription());
                rs.setString(3, role.getStatus());
                rs.setInt(4, role.getId());

                return rs.executeUpdate()==1 ;
            }

    }

    @Override
    public boolean delete(int id) throws SQLException {

            String sql = "DELETE FROM roles WHERE id=?";

            try (PreparedStatement rs = cnx.prepareStatement(sql)) {

                rs.setInt(1, id);

                return rs.executeUpdate()==1 ;
            }

    }

    @Override
    public boolean delete(Role role) throws SQLException {
        return delete(role.getId());
    }

    @Override
    public List<Role> selectAll() throws SQLException {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles";

        try (PreparedStatement rs = cnx.prepareStatement(sql);
             ResultSet resultSet = rs.executeQuery()) {

            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setName(RoleName.valueOf(resultSet.getString("name")));
                role.setDescription(resultSet.getString("description"));
                role.setStatus(resultSet.getString("status"));

                roles.add(role);
            }
        }

        return roles;
    }
}
