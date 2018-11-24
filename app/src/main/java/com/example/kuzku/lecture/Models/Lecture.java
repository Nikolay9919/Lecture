package com.example.kuzku.lecture.Models;

public class Lecture {

    private int id;
    private String name;
    private String content;

    public Lecture() {
    }

    public Lecture(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                  "name='" + name + '\'' +
                  ", content='" + content + '\'' +
                  '}';
    }
}
