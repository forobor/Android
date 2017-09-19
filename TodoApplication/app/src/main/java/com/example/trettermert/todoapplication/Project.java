package com.example.trettermert.todoapplication;

import java.util.ArrayList;

public class Project {

    private String title;
    private ArrayList<Todo> todos;

    public Project (String title){
        this.title = title;
        todos = new ArrayList<>();
    }

    public static String[] getTitles(ArrayList<Project> projects) {
        String[] titles = new String[projects.size()];
        for(int i = 0; i < projects.size(); i++){
            titles[i] = projects.get(i).getTitle();

        }
        return titles;
    }

    public void  addTodo(String text){
        todos.add(new Todo(text));
    }

    public void  addTodo(String text, boolean isCompleted){
        todos.add(new Todo(text, isCompleted));
    }

    public Todo getTodoAtIndex(int i){
        return  todos.get(i);
    }

    public  int getTodosCount(){
        return  todos.size();
    }

    public String getTitle(){
        return title;
    }
}