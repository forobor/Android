package com.example.trettermert.todoapplication;

public class Todo {

    private String text;
    private boolean isCompleted;

    public Todo (String text){
        this.text = text;
    }

    public Todo (String text, boolean isCompleted){
        this.text = text;
        this.isCompleted=isCompleted;
    }

    public String getText() {
        return text;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}