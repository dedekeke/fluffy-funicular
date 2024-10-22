package com.chaunhat.fluffyfunicular.dao;

import com.chaunhat.fluffyfunicular.model.Account;
import com.chaunhat.fluffyfunicular.util.DatabaseUtility;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AccountDAO {
    @Autowired
    private DatabaseUtility databaseUtility;
    public Account getUserByEmailAndPassword(String userEmail, String password) throws SQLException {
        Account user = null;
        String sql = "SELECT * FROM \"Account\" WHERE user_mail = ? AND password = ?";
        try (Connection conn = databaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userEmail);
            stmt.setString(2, password);

            user = getUser(stmt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }


    public Account getUserByEmail(String email) {
        Account user = null;
        String sql = "SELECT * FROM \"Account\" WHERE user_mail = ?";
        try (Connection conn = databaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);

            user = getUser(stmt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public boolean insertAccount(Account newAccount) {
        String sql = "INSERT INTO \"Account\" (user_name, user_mail, password, account_role, user_address, user_phone) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = databaseUtility.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newAccount.name());
            stmt.setString(2, newAccount.email());
            stmt.setString(3, newAccount.password());
            stmt.setInt(4, newAccount.account_role());
            stmt.setString(5, newAccount.address());
            stmt.setString(6, newAccount.phone());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Nullable
    private Account getUser(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Account(
                        rs.getString("user_name"),
                        rs.getString("user_mail"),
                        rs.getString("password"),
                        rs.getInt("account_role"),
                        rs.getString("user_address"),
                        rs.getString("user_phone")
                );
            }
        }
        return null;
    }
}
