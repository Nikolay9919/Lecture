package com.example.kuzku.lecture.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kuzku.lecture.Models.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, DatabaseOptions.DB_NAME, null, DatabaseOptions.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseOptions.CREATE_USERS_TABLE);
        db.execSQL(DatabaseOptions.CREATE_LECTURE_TABLE);
        db.execSQL(DatabaseOptions.CREATE_LECTURERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseOptions.USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseOptions.LECTURES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseOptions.LECTURERS_TABLE);
        onCreate(db);
    }

    public User queryUser(String email, String password, boolean isLecturer) {

        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(DatabaseOptions.USERS_TABLE, new String[]{DatabaseOptions.UserId,
                            DatabaseOptions.studNumber, DatabaseOptions.password, DatabaseOptions.isLecturer}, DatabaseOptions.studNumber + "=? and " + DatabaseOptions.password + "=?" + DatabaseOptions.isLecturer + "=?",
                  new String[]{email, password, String.valueOf(isLecturer)}, null, null, null, "1");
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
}
