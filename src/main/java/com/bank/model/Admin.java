package com.bank.model;

public class Admin {
    private String username;
    private String password; // Hashed password for security

    public Admin() {
    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Method to validate the admin login
    public boolean validateAdmin(String username, String password) {
        // This is a simple validation. In real-world scenarios, you should hash the password
        // and compare it with the hashed password stored in the database.
        return this.username.equals(username) && this.password.equals(password);
    }
}