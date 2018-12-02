package com.example.kuzku.lecture.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kuzku.lecture.Database.DatabaseHelper;
import com.example.kuzku.lecture.Models.Lecture;
import com.example.kuzku.lecture.Models.Lecturer;
import com.example.kuzku.lecture.R;

public class LectureActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView contentLectureView;
    Lecture lecture;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState = getIntent().getExtras();
        databaseHelper = new DatabaseHelper(getApplicationContext());

        lecture = databaseHelper.getLectures(savedInstanceState.getInt("id"));

        setContentView(R.layout.activity_lecture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(lecture.getName());
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                  this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onResume() {
        super.onResume();

        Lecturer lecturer = databaseHelper.getLecturer(lecture.getLecturerId());


        contentLectureView = (TextView) findViewById(R.id.content_lecture);
        contentLectureView.setMovementMethod(new ScrollingMovementMethod());
        contentLectureView.setText(lecture.getName() + " \n " + lecturer.getFName() + " " + lecturer.getLName()
                  + " \n " + lecture.getContent());

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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_lecture) {
            Intent intent = new Intent(this, AddLectureActivity.class);
            startActivity(intent);
        } else if (id == R.id.add_lecturer) {
            Intent intent = new Intent(this, AddLecturerActivity.class);
            startActivity(intent);

        } else if (id == R.id.update_user) {
            Intent intent = new Intent(this, EditUserActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
