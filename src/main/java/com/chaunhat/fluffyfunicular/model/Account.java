package com.chaunhat.fluffyfunicular.model;

import com.chaunhat.fluffyfunicular.dao.AccountDAO;

import java.sql.SQLException;

public record Account(String name, String email, String password, int account_role, String address, String phone) {

    public static Account getAccount(String email, String password) throws SQLException {
        AccountDAO accountDAO = new AccountDAO();
        return accountDAO.getUserByEmailAndPassword(email, password);
    }

    public static Account getAccountByEmail(String email) throws SQLException {
        AccountDAO accountDAO = new AccountDAO();
        return accountDAO.getUserByEmail(email);
    }

    public static boolean createAccount(String name, String email, String password, boolean isAdmin, String address, String phone) throws SQLException {
        AccountDAO accountDAO = new AccountDAO();
        int account_role = 2;
        // Check if the account with the same email already exists
        Account existingAccount = accountDAO.getUserByEmail(email);
        if (existingAccount != null) {
            return false;
        }
        if (isAdmin) account_role = 1;

        // If the account does not exist, create a new one
        Account newAccount = new Account(name, email, password, account_role, address, phone);
        return accountDAO.insertAccount(newAccount);
    }
}
