package com.chaunhat.fluffyfunicular.service;

import com.chaunhat.fluffyfunicular.dao.AccountDAO;
import com.chaunhat.fluffyfunicular.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class AccountService {

    private final AccountDAO accountDAO;

    @Autowired
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account getAccount(String email, String password) throws SQLException {
        return accountDAO.getUserByEmailAndPassword(email, password);
    }

    public Account getAccountByEmail(String email) throws SQLException {
        return accountDAO.getUserByEmail(email);
    }

    public boolean createAccount(String name, String email, String password, boolean isAdmin, String address, String phone) throws SQLException {
        int account_role = isAdmin ? 1 : 2;

        // Check if the account with the same email already exists
        Account existingAccount = accountDAO.getUserByEmail(email);
        if (existingAccount != null) {
            return false;
        }

        // If the account does not exist, create a new one
        Account newAccount = new Account(name, email, password, account_role, address, phone);
        return accountDAO.insertAccount(newAccount);
    }
}
