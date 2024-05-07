package com.chaunhat.prj321asm1.model;

import java.util.UUID;

public record User(UUID id, String name, String email, String password, String confirmPassword, boolean isAdmin) {
    public static User createUser(String name, String email, String password, String confirmPassword, boolean isAdmin) {
        UUID id = UUID.randomUUID();
        return new User(id, name, email, password, confirmPassword, isAdmin);
    }
}
