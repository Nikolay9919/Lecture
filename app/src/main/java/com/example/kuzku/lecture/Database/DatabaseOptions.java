package com.example.kuzku.lecture.Database;

public class DatabaseOptions {

    public static final String DB_NAME = "Lecture.db";
    public static final int DB_VERSION = 1;

    public static final String USERS_TABLE = "users";
    public static final String LECTURES_TABLE = "lectures";
    public static final String LECTURERS_TABLE = "lecturers";

    public static final String LectureId = "_id";
    public static final String LecturerID = "LecturerID";
    public static final String UserId = "id";

    public static final String LectureName = "LectureName";
    public static final String LectureContent = "LectureContent";


    public static final String LecturerFName = "LecturerFName";
    public static final String LecturerLName = "LecturerLName";

    public static final String studNumber = "studNumber";
    public static final String password = "password";
    public static final String isLecturer = "isLecturer";

    public static final String CREATE_LECTURE_TABLE =
              "CREATE TABLE  " + LECTURES_TABLE + "("
                        + LectureId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + LectureName + " TEXT NOT NULL,"
                        + LectureContent + " TEXT NOT NULL , "
                        + LecturerID + " INTEGER, " + " FOREIGN KEY ( "
                        + LecturerID + " ) REFERENCES " + LECTURERS_TABLE + " ( "
                        + LecturerID + " ) "
                        + ");";

    public static final String CREATE_LECTURERS_TABLE =
              "CREATE TABLE  " + LECTURERS_TABLE + " ( "
                        + LecturerID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + LecturerFName + " TEXT NOT NULL, "
                        + LecturerLName + " TEXT NOT NULL, "
                        + LectureId + " INTEGER, " + "FOREIGN KEY ( "
                        + LectureId + " ) REFERENCES " + LECTURES_TABLE + " ( "
                        + LectureId + " ));";

    public static final String CREATE_USERS_TABLE =
              "CREATE TABLE  " + USERS_TABLE + " ( "
                        + UserId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + studNumber + " TEXT NOT NULL, "
                        + password + " VARCHAR(25) NOT NULL , "
                        + isLecturer + " BOOLEAN " + ");";

    public static final String INSERT = "INSERT INTO " + USERS_TABLE + " ( " + studNumber + " , " +
              password + " , " + isLecturer + ") VALUES ('1234','qwerty', 1);";


}
