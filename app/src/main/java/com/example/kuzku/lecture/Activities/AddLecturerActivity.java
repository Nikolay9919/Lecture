package com.example.kuzku.lecture.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kuzku.lecture.Database.DatabaseHelper;
import com.example.kuzku.lecture.Database.DatabaseOptions;
import com.example.kuzku.lecture.Models.Lecturer;
import com.example.kuzku.lecture.R;

public class AddLecturerActivity extends AppCompatActivity {
    EditText fNameBox;
    EditText lNameBox;
    Button addButton;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lecturer);

        fNameBox = (EditText) findViewById(R.id.lecturerFNameInput);
        lNameBox = (EditText) findViewById(R.id.lecturerLNameInput);
        addButton = (Button) findViewById(R.id.addLecturer);
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ContentValues cv = new ContentValues();

                if (!emptyValidation()) {
                    Lecturer lecturer = new Lecturer();
                    lecturer.setFName(fNameBox.getText().toString());
                    lecturer.setLName(lNameBox.getText().toString());

                    databaseHelper.addLecturer(lecturer);
                    Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
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
        if (TextUtils.isEmpty(fNameBox.getText().toString()) || TextUtils.isEmpty(lNameBox.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }
}
