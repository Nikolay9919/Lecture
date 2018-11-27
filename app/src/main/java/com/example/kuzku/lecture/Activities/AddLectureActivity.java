package com.example.kuzku.lecture.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kuzku.lecture.Database.DatabaseHelper;
import com.example.kuzku.lecture.Database.DatabaseOptions;
import com.example.kuzku.lecture.R;

public class AddLectureActivity extends AppCompatActivity {
    EditText nameBox;
    EditText contentBox;
    Button addButton;
    String name;
    String content;
    int lecturerId;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Spinner spinner;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_lecture);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, databaseHelper.getLecturersName());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Lecturers");
        nameBox = (EditText) findViewById(R.id.lectureNameInput);
        contentBox = (EditText) findViewById(R.id.contentInput);
        addButton = (Button) findViewById(R.id.saveButton);
        name = nameBox.getText().toString();
        content = contentBox.getText().toString();
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lecturerId = position;
            }
        });
        final ContentValues cv = new ContentValues();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.isEmpty())
                    Toast.makeText(getApplicationContext(), "Please,enter a name of lecture", Toast.LENGTH_SHORT).show();
                else if (content.isEmpty())
                    Toast.makeText(getApplicationContext(), "Please,enter a content of lecture", Toast.LENGTH_SHORT).show();
                else
                    cv.put(DatabaseOptions.LectureName, name);
                cv.put(DatabaseOptions.LectureContent, content);
                cv.put(DatabaseOptions.LecturerID, lecturerId);


            }
        });


    }
}
