package com.example.kuzku.lecture.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kuzku.lecture.Models.Lecture;
import com.example.kuzku.lecture.Models.Lecturer;
import com.example.kuzku.lecture.Models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, DatabaseOptions.DB_NAME, null, DatabaseOptions.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseOptions.CREATE_USERS_TABLE);
        db.execSQL(DatabaseOptions.CREATE_LECTURE_TABLE);
        db.execSQL(DatabaseOptions.CREATE_LECTURERS_TABLE);
        db.execSQL(DatabaseOptions.INSERT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseOptions.USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseOptions.LECTURES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseOptions.LECTURERS_TABLE);
        onCreate(db);
    }

    public User queryUser(String studNumber, String password) {

        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(DatabaseOptions.USERS_TABLE, new String[]{DatabaseOptions.UserId,
                            DatabaseOptions.studNumber, DatabaseOptions.password, DatabaseOptions.isLecturer}, DatabaseOptions.studNumber + "=? and " + DatabaseOptions.password + "=?",
                  new String[]{studNumber, password}, null, null, null, "1");
        if (cursor != null)
            cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            user = new User(cursor.getString(1), cursor.getString(2), Boolean.parseBoolean(cursor.getString(3)));
        }
        // return user
        return user;
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseOptions.studNumber, user.getStudNumber());
        values.put(DatabaseOptions.password, user.getPassword());
        values.put(DatabaseOptions.isLecturer, user.isLecturer());
        // Inserting Row
        db.insert(DatabaseOptions.USERS_TABLE, null, values);
        db.close(); // Closing database connection

    }

    public Lecture getLectures(int lecture_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DatabaseOptions.LECTURES_TABLE + " WHERE "
                  + DatabaseOptions.LectureId + " = " + lecture_id;

        Log.e("lecture", selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        Lecture lecture = new Lecture();
        lecture.setId(cursor.getInt(cursor.getColumnIndex(DatabaseOptions.LectureId)));
        lecture.setName(cursor.getString(cursor.getColumnIndex(DatabaseOptions.LectureName)));
        lecture.setContent(cursor.getString(cursor.getColumnIndex(DatabaseOptions.LectureContent)));
        lecture.setLecturerId(cursor.getInt(cursor.getColumnIndex(DatabaseOptions.LecturerID)));
        return lecture;

    }

    public List<Lecture> getAllLectures() {
        List<Lecture> lectures = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseOptions.LECTURES_TABLE;

        Log.e("lectures", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
            do {
                Lecture lecture = new Lecture();
                lecture.setId(cursor.getInt(cursor.getColumnIndex(DatabaseOptions.LectureId)));
                lecture.setName(cursor.getString(cursor.getColumnIndex(DatabaseOptions.LectureName)));
                lecture.setContent(cursor.getString(cursor.getColumnIndex(DatabaseOptions.LectureContent)));
                lecture.setLecturerId(cursor.getInt(cursor.getColumnIndex(DatabaseOptions.LecturerID)));
                lectures.add(lecture);

            } while (cursor.moveToNext());
        return lectures;

    }

    public int updateLectures(Lecture lecture) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseOptions.LectureName, lecture.getName());
        values.put(DatabaseOptions.LectureContent, lecture.getContent());
        values.put(DatabaseOptions.LecturerID, lecture.getLecturerId());

        return db.update(DatabaseOptions.LECTURES_TABLE, values, DatabaseOptions.LectureId + " = ?",
                  new String[]{String.valueOf(lecture.getId())});
    }

    public ArrayList<String> getLectures() {
        ArrayList<String> lectures = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorLectures = db.rawQuery("select * from " + DatabaseOptions.LECTURES_TABLE, null);

        if (cursorLectures.moveToFirst())
            do {

                lectures.add(cursorLectures.getString(cursorLectures.getColumnIndex(DatabaseOptions.LectureName)) + "        "
                          + getLecturerName(cursorLectures.getInt(cursorLectures.getColumnIndex(DatabaseOptions.LecturerID)) + 1));


            } while (cursorLectures.moveToNext());
        return lectures;


    }

    public void deleteLecture(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseOptions.LECTURES_TABLE, DatabaseOptions.LectureId + " = ?",
                  new String[]{String.valueOf(DatabaseOptions.LectureId)});
    }

    public void addLecture(Lecture lecture) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseOptions.LectureName, lecture.getName());
        values.put(DatabaseOptions.LectureContent, lecture.getContent());
        values.put(DatabaseOptions.LecturerID, lecture.getId());
        db.insert(DatabaseOptions.LECTURES_TABLE, null, values);
        db.close();
    }

    public List<String> getAllLecturers() {
        List<String> lecturers = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseOptions.LECTURERS_TABLE;

        Log.e("lecturers", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseOptions.LECTURERS_TABLE, null);

        if (cursor.moveToFirst())
            do {

                lecturers.add(cursor.getString(cursor.getColumnIndex(DatabaseOptions.LecturerFName)) + " "
                          + cursor.getString(cursor.getColumnIndex(DatabaseOptions.LecturerLName)));


            } while (cursor.moveToNext());
        return lecturers;

    }


    public void addLecturer(Lecturer lecturer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseOptions.LecturerFName, lecturer.getFName());
        cv.put(DatabaseOptions.LecturerLName, lecturer.getLName());
        cv.put(DatabaseOptions.LectureId, lecturer.getLectureId());
        db.insert(DatabaseOptions.LECTURERS_TABLE, null, cv);
        Log.d("lecturer", cv.toString());
        db.close();
    }

    public Lecturer getLecturer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DatabaseOptions.LECTURERS_TABLE + " WHERE "
                  + DatabaseOptions.LecturerID + " = " + id;

        Log.e("lecture", selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        Lecturer lecturer = new Lecturer();
        lecturer.setId(cursor.getInt(cursor.getColumnIndex(DatabaseOptions.LectureId)));
        lecturer.setFName(cursor.getString(cursor.getColumnIndex(DatabaseOptions.LecturerFName)));
        lecturer.setLName(cursor.getString(cursor.getColumnIndex(DatabaseOptions.LecturerLName)));
        lecturer.setLectureId(cursor.getInt(cursor.getColumnIndex(DatabaseOptions.LectureId)));
        return lecturer;
    }

    public String getLecturerName(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String name;

        String selectQuery = "SELECT * FROM " + DatabaseOptions.LECTURERS_TABLE + " WHERE "
                  + DatabaseOptions.LecturerID + " = " + id;

        Log.e("lecturer", selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        name = cursor.getString(cursor.getColumnIndex(DatabaseOptions.LecturerFName)) + " "
                  + cursor.getString(cursor.getColumnIndex(DatabaseOptions.LecturerLName));

        return name;
    }
}
