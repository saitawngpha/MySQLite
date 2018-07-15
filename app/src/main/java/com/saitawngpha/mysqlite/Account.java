package com.saitawngpha.mysqlite;

public class Account {

    private String userName;
    private String Email;

    public Account() {
    }

    public Account(String userName, String email) {
        this.userName = userName;
        Email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
