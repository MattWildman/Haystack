package com.haystack.entities;

public class Registration {
        
	private String username;
    private String password;
    private String confirmPassword;
    private String email;

    public void setUsername(String username) {
            this.username = username;
    }

    public String getUsername() {
            return username;
    }

    public void setPassword(String password) {
            this.password = password;
    }

    public String getPassword() {
            return password;
    }

    public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
            return confirmPassword;
    }

    public void setEmail(String email) {
            this.email = email;
    }

    public String getEmail() {
            return email;
    }

}
