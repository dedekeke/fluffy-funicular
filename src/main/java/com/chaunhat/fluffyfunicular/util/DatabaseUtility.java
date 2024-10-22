package com.chaunhat.fluffyfunicular.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseUtility {
    private static DataSource dataSource;

    public DatabaseUtility(DataSource dataSource) {
        DatabaseUtility.dataSource = dataSource;
    }

    // Method to get a connection from the DataSource
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
