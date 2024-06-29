package com.chaunhat.fluffyfunicular.listener;

import com.chaunhat.fluffyfunicular.util.DataSeeder;
import com.chaunhat.fluffyfunicular.util.DatabaseUtility;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import javax.sql.DataSource;

@WebListener
public class AppContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();

        // Initialize database connection
//        CREATE TABLE products (
//                id SERIAL PRIMARY KEY,
//                name VARCHAR(255),
//                description TEXT,
//                quantity INT,
//                price NUMERIC(10, 2),
//                image BYTEA
//        );

        String dbURL = ctx.getInitParameter("DBURL");
        String user = ctx.getInitParameter("DBUSER");
        String pwd = ctx.getInitParameter("DBPWD");

        try {
            DataSource ds = DatabaseUtility.setupDataSource(dbURL, user, pwd);
            System.out.println("Database connection initialized for Application.");
//            DataSeeder.seedProducts(ds.getConnection());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // Close resources here
        try {
            DatabaseUtility.closeDataSource();
            System.out.println("Closed ");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
