package com.example.kuzku.lecture.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import android.widget.EditText;

import com.example.kuzku.lecture.Database.DatabaseHelper;
import com.example.kuzku.lecture.Models.Lecturer;
import com.example.kuzku.lecture.R;

public class AddLecturerActivity extends AppCompatActivity implements
          NavigationView.OnNavigationItemSelectedListener {
    EditText fNameBox;
    EditText lNameBox;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    int isLecturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lecturer);

        fNameBox = findViewById(R.id.lecturerFNameInput);
        lNameBox = findViewById(R.id.lecturerLNameInput);
        final Intent intent = getIntent();
        isLecturer = intent.getIntExtra("isLecturer", isLecturer);
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!emptyValidation()) {
                    Lecturer lecturer = new Lecturer();
                    lecturer.setFName(fNameBox.getText().toString());
                    lecturer.setLName(lNameBox.getText().toString());

                    databaseHelper.addLecturer(lecturer);
                    Snackbar.make(view, "Added", Snackbar.LENGTH_LONG)
                              .setAction("Action", null).show();
                    goHome();
                } else {
                    Snackbar.make(view, "Empty Fields", Snackbar.LENGTH_LONG)
                              .setAction("Action", null).show();
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
        return TextUtils.isEmpty(fNameBox.getText().toString()) || TextUtils.isEmpty(lNameBox.getText().toString());
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
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_lecture) {
            Intent intent = new Intent(this, AddLectureActivity.class);
            startActivity(intent);
        } else if (id == R.id.update_user) {
            Intent intent = new Intent(this, EditUserActivity.class);
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
