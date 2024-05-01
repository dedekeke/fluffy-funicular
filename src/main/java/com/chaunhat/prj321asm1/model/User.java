package com.chaunhat.prj321asm1.model;

import java.util.UUID;

public record User(UUID id, String name, String email, String password, String confirmPassword) {
    public static User createUser(String name, String email, String password, String confirmPassword) {
        UUID id = UUID.randomUUID();
        return new User(id, name, email, password, confirmPassword);
    }
}
