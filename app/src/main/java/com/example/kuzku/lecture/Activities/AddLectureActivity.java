package com.example.kuzku.lecture.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.example.kuzku.lecture.Models.Lecture;
import com.example.kuzku.lecture.Models.Lecturer;
import com.example.kuzku.lecture.R;

import java.util.ArrayList;
import java.util.List;

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

        databaseHelper = new DatabaseHelper(getApplicationContext());
        List<Lecturer> lecturers = new ArrayList<>();
        Lecturer lecturer = new Lecturer();
        lecturer.setFName("Nikolay");
        lecturer.setLName("Kuzichev");
        lecturers.add(lecturer);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, databaseHelper.getAllLecturers());
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lecturerId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final ContentValues cv = new ContentValues();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (name.matches(""))
//                    Toast.makeText(getApplicationContext(), "Please,enter a name of lecture", Toast.LENGTH_SHORT).show();
//                else if (content.matches(""))
//                    Toast.makeText(getApplicationContext(), "Please,enter a content of lecture", Toast.LENGTH_SHORT).show();

                if (!emptyValidation()) {
                    Lecture lecture = new Lecture();
                    lecture.setName(nameBox.getText().toString());
                    lecture.setContent(contentBox.getText().toString());
                    lecture.setLecturerId(spinner.getSelectedItemPosition());
                    databaseHelper.addLecture(lecture);
                } else {
                    Toast.makeText(getApplicationContext(), "Empty fields", Toast.LENGTH_SHORT).show();
                }

                goHome();

            }
        });


    }

    private void goHome() {

        db.close();

        Intent intent = new Intent(this, Main2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(nameBox.getText().toString()) || TextUtils.isEmpty(contentBox.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }
}
