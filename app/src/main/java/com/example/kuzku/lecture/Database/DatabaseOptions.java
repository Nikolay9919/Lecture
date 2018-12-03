package com.example.kuzku.lecture.Database;

public class DatabaseOptions {

    static final String DB_NAME = "Lecture.db";
    static final int DB_VERSION = 1;

    static final String USERS_TABLE = "users";
    static final String LECTURES_TABLE = "lectures";
    static final String LECTURERS_TABLE = "lecturers";

    static final String LectureId = "_id";
    static final String LecturerID = "LecturerID";
    static final String UserId = "id";

    static final String LectureName = "LectureName";
    static final String LectureContent = "LectureContent";


    static final String LecturerFName = "LecturerFName";
    static final String LecturerLName = "LecturerLName";

    static final String studNumber = "studNumber";
    public static final String password = "password";
    public static final String isLecturer = "isLecturer";

    static final String CREATE_LECTURE_TABLE =
              "CREATE TABLE  " + LECTURES_TABLE + "("
                        + LectureId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + LectureName + " TEXT NOT NULL,"
                        + LectureContent + " TEXT NOT NULL , "
                        + LecturerID + " INTEGER, " + " FOREIGN KEY ( "
                        + LecturerID + " ) REFERENCES " + LECTURERS_TABLE + " ( "
                        + LecturerID + " ) "
                        + ");";

    static final String CREATE_LECTURERS_TABLE =
              "CREATE TABLE  " + LECTURERS_TABLE + " ( "
                        + LecturerID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + LecturerFName + " TEXT NOT NULL, "
                        + LecturerLName + " TEXT NOT NULL, "
                        + LectureId + " INTEGER, " + "FOREIGN KEY ( "
                        + LectureId + " ) REFERENCES " + LECTURES_TABLE + " ( "
                        + LectureId + " ));";

    static final String CREATE_USERS_TABLE =
              "CREATE TABLE  " + USERS_TABLE + " ( "
                        + UserId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + studNumber + " TEXT NOT NULL, "
                        + password + " VARCHAR(25) NOT NULL , "
                        + isLecturer + " BOOLEAN " + ");";

    static final String INSERT = "INSERT INTO " + USERS_TABLE + " ( " + studNumber + " , " +
              password + " , " + isLecturer + ") VALUES ('1234','qwerty', 1);";


}
