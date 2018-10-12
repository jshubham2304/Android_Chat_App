package com.androidchatapp;

public class MeassageData {
    String message;
    String date;
    String Username;

    public MeassageData(String message, String date, String username) {
        this.message = message;
        this.date = date;
        Username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
