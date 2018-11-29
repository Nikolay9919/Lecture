package com.example.kuzku.lecture.Models;

public class User {

    private int id;
    private String studNumber;
    private String password;
    private boolean isLecturer;

    public User(String studNumber, String password, boolean isLecturer) {
        this.studNumber = studNumber;
        this.password = password;
        this.isLecturer = isLecturer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudNumber() {
        return studNumber;
    }

    public void setStudNumber(String studNumber) {
        this.studNumber = studNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLecturer() {
        return isLecturer;
    }

    public void setLecturer(boolean lecturer) {
        isLecturer = lecturer;
    }

    @Override
    public String toString() {
        return "User{" +
                  "id=" + id +
                  ", studNumber='" + studNumber + '\'' +
                  ", password='" + password + '\'' +
                  ", isLecturer=" + isLecturer +
                  '}';
    }


}
