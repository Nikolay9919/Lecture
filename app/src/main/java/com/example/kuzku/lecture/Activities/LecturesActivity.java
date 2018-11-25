package com.example.kuzku.lecture.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.kuzku.lecture.Database.DatabaseHelper;
import com.example.kuzku.lecture.Database.DatabaseOptions;
import com.example.kuzku.lecture.R;

public class LecturesActivity extends AppCompatActivity {

    ListView lectureList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter lectureAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures);


        lectureList = (ListView) findViewById(R.id.list);
        lectureList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), LectureActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(getApplicationContext());

    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] headers = new String[]{DatabaseOptions.LectureName};


        SimpleCursorAdapter lecturesAdapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.two_line_list_item,
                  databaseHelper.getLecturesCursor(), headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        lectureList.setAdapter(lecturesAdapter);

    }
}