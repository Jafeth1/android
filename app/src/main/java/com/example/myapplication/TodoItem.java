package com.example.myapplication;

public class TodoItem {
    private String text;
    private boolean urgent;

    public TodoItem(String text, boolean urgent) {
        this.text = text;
        this.urgent = urgent;
    }

    public String getText() {
        return text;
    }

    public boolean isUrgent() {
        return urgent;
    }
}
