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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kuzku.lecture.Database.DatabaseHelper;
import com.example.kuzku.lecture.R;

public class Main2Activity extends AppCompatActivity
          implements NavigationView.OnNavigationItemSelectedListener {
    ListView lectureList;
    DatabaseHelper databaseHelper;
    int userId;

    int isLecturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = getIntent();
        userId = intent.getIntExtra("id", userId);
        isLecturer = intent.getIntExtra("isLecturer", isLecturer);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                  this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        lectureList = (ListView) findViewById(R.id.list);
        lectureList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), LectureActivity.class);
                int lectureId = (int) (id + 1);
                intent.putExtra("id", lectureId);
                Log.d("position", String.valueOf(id));
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, databaseHelper.getLectures());
        Log.d("lectures", databaseHelper.getAllLectures().toString());
        Log.d("leturers", databaseHelper.getAllLecturers().toString());
        lectureList.setAdapter(adapter);

    }

    public void add(View v) {
        Intent intent = new Intent(this, AddLectureActivity.class);
        startActivity(intent);
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
            if (isLecturer == 1) {
                Intent intent = new Intent(this, AddLectureActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(Main2Activity.this, "You have no permission", Toast.LENGTH_SHORT);
            }

        } else if (id == R.id.add_lecturer) {
            if (isLecturer == 1) {
                Intent intent = new Intent(this, AddLecturerActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(Main2Activity.this, "You have no permission", Toast.LENGTH_SHORT);
            }
        } else if (id == R.id.update_user) {

            Intent intent = new Intent(this, EditUserActivity.class);
            intent.putExtra("id", userId);
            intent.putExtra("isLecturer", isLecturer);
            Log.d("idinmain2activity", String.valueOf(userId));
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
