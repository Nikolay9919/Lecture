package com.example.kuzku.lecture.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kuzku.lecture.Database.DatabaseHelper;
import com.example.kuzku.lecture.Models.Lecture;
import com.example.kuzku.lecture.Models.Lecturer;
import com.example.kuzku.lecture.R;

public class LectureActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView nameLectureView;
    TextView contentLectureView;
    TextView nameLecturerView;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lecture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                  this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        savedInstanceState = getIntent().getExtras();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        Lecture lecture = databaseHelper.getLectures(savedInstanceState.getInt("id"));
        Lecturer lecturer = databaseHelper.getLecturer(lecture.getLecturerId());
        Log.d("lecturerId", String.valueOf(lecture.getLecturerId()));


        nameLectureView = (TextView) findViewById(R.id.name_lecture);
        contentLectureView = (TextView) findViewById(R.id.content_lecture);
        nameLecturerView = (TextView) findViewById(R.id.lecturer_name);
        Log.d("extras", String.valueOf(savedInstanceState.getInt("id")));

        nameLectureView.setText("Name:\n" + lecture.getName());
        nameLecturerView.setText("Lecturer:\n" + lecturer.getFName() + " " + lecturer.getLName());
        contentLectureView.setText(lecture.getContent() + " ");

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_lecture) {
            Intent intent = new Intent(this, AddLectureActivity.class);
            startActivity(intent);
        } else if (id == R.id.add_lecturer) {
            Intent intent = new Intent(this, AddLecturerActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
