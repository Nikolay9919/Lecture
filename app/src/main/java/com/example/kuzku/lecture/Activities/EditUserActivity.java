package com.example.kuzku.lecture.Activities;

import android.content.Intent;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.kuzku.lecture.Database.DatabaseHelper;
import com.example.kuzku.lecture.Models.User;
import com.example.kuzku.lecture.R;

public class EditUserActivity extends AppCompatActivity
          implements NavigationView.OnNavigationItemSelectedListener {

    EditText studNumber;
    EditText password;
    DatabaseHelper databaseHelper;
    CheckBox checkBox;
    FloatingActionButton fab;
    User user;
    int isLecturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final Intent intent = getIntent();
        isLecturer = intent.getIntExtra("isLecturer", isLecturer);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                  this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        studNumber = (EditText) findViewById(R.id.edit_stud_number);
        password = (EditText) findViewById(R.id.edit_password);
        checkBox = (CheckBox) findViewById(R.id.is_Lecturer);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        if (isLecturer == 0) {
            checkBox.setVisibility(View.INVISIBLE);
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("userineditactivity", studNumber.getText().toString() + password.getText().toString() + checkBox.isChecked());
                if (!emptyValidation()) {
                    int id = 0;
                    user = new User();

                    user.setStudNumber(studNumber.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setIsLecturer(checkBox.isChecked());
                    id = intent.getIntExtra("id", id);
                    databaseHelper.updateUser(user, id);

                    Snackbar.make(view, "User updated", Snackbar.LENGTH_LONG)
                              .setAction("Action", null).show();

                } else {
                    Snackbar.make(view, "Empty Fields", Snackbar.LENGTH_LONG)
                              .setAction("Action", null).show();
                }


                goHome();
            }
        });
    }


    @Override
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
        getMenuInflater().inflate(R.menu.edit_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
        } else if (id == R.id.add_lecturer) {
            Intent intent = new Intent(this, AddLecturerActivity.class);
            startActivity(intent);

        } else if (id == R.id.registration_button) {
            Intent intent = new Intent(this, RegistrationActivity.class);
            intent.putExtra("isLecturer", isLecturer);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(studNumber.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    private void goHome() {

        databaseHelper.close();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
