package com.example.kuzku.lecture.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kuzku.lecture.Database.DatabaseHelper;
import com.example.kuzku.lecture.Models.Lecture;
import com.example.kuzku.lecture.R;

public class AddLectureActivity extends AppCompatActivity implements
          NavigationView.OnNavigationItemSelectedListener {
    EditText nameBox;
    EditText contentBox;
    String name;
    String content;
    int lecturerId;
    int lectureIdUpdate;
    int lectureId;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    int isLecturer;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_lecture);
        final Intent intent = getIntent();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        isLecturer = intent.getIntExtra("isLecturer", isLecturer);
        lectureIdUpdate = intent.getIntExtra("lectureId", lectureId);


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, databaseHelper.getAllLecturers());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Lecturers");
        nameBox = findViewById(R.id.lectureNameInput);
        contentBox = findViewById(R.id.contentInput);

        name = nameBox.getText().toString();
        content = contentBox.getText().toString();
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();
        if (lectureIdUpdate != 0) {
            Lecture lecture;
            lecture = databaseHelper.getLectures(lectureIdUpdate);
            nameBox.setText(lecture.getName());
            contentBox.setText(lecture.getContent());
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lecturerId = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lecture lecture = new Lecture();
                if (lectureIdUpdate != 0) {
                    lecture.setId(lectureIdUpdate);
                    lecture.setName(nameBox.getText().toString());
                    lecture.setContent(contentBox.getText().toString());
                    lecture.setLecturerId(lecturerId);
                    databaseHelper.updateLecture(lecture);
                    goHome();
                } else {
                    if (!emptyValidation()) {

                        lecture.setName(nameBox.getText().toString());
                        lecture.setContent(contentBox.getText().toString());
                        lecture.setLecturerId(lecturerId);

                        databaseHelper.addLecture(lecture);
                        Snackbar.make(view, "Added", Snackbar.LENGTH_LONG)
                                  .setAction("Action", null).show();
                        goHome();
                    } else {
                        Snackbar.make(view, "Empty Fields", Snackbar.LENGTH_LONG)
                                  .setAction("Action", null).show();
                    }


                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                  this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    private void goHome() {

        db.close();

        Intent intent = new Intent(this, Main2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private boolean emptyValidation() {
        return TextUtils.isEmpty(nameBox.getText().toString()) || TextUtils.isEmpty(contentBox.getText().toString());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.update_user) {
            Intent intent = new Intent(this, EditUserActivity.class);
            startActivity(intent);
        } else if (id == R.id.add_lecturer) {
            Intent intent = new Intent(this, AddLecturerActivity.class);
            startActivity(intent);

        } else if (id == R.id.registration_button) {
            Intent intent = new Intent(this, RegistrationActivity.class);
            intent.putExtra("isLecturer", isLecturer);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
