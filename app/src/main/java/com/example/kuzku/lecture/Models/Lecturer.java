package com.example.kuzku.lecture.Models;

public class Lecturer {
    private int id;
    private String FName;
    private String LName;
    private int lectureId;
    private Lecture lecture;

    public Lecturer() {
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public Lecturer(String FName, String LName, int lectureId) {
        this.FName = FName;
        this.LName = LName;
        this.lectureId = lectureId;
    }

    public Lecturer(String FName, String LName) {
        this.FName = FName;
        this.LName = LName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                  "FName='" + FName + '\'' +
                  ", LName='" + LName + '\'' +
                  ", lectureId=" + lectureId +
                  '}';
    }
}
