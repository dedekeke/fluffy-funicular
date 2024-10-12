package com.chaunhat.fluffyfunicular.util;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtility {
    private static BasicDataSource dataSource = null;

    public static DataSource setupDataSource(String url, String username, String pwd) {
        if (dataSource == null) {
            BasicDataSource ds = new BasicDataSource();
            ds.setUrl(url);
            ds.setUsername(username);
            ds.setPassword(pwd);
            ds.setDriverClassName("org.postgresql.Driver");
            dataSource = ds;
        }
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeDataSource() {
        if (dataSource != null) {
            try {
                dataSource.close();
                dataSource = null;
                System.out.println("DataSource has been closed successfully.");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
