package com.example.kuzku.lecture.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kuzku.lecture.Database.DatabaseHelper;
import com.example.kuzku.lecture.Models.User;
import com.example.kuzku.lecture.R;

public class MainActivity extends AppCompatActivity {

    EditText editStudNumber;
    EditText editPassword;
    Button signInButton;
    Button registrationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editStudNumber = findViewById(R.id.stud_number_input);
        editPassword = findViewById(R.id.password_input);
        signInButton = findViewById(R.id.sign_in_button);
        registrationButton = findViewById(R.id.registration_button);

        final DatabaseHelper databaseHelper = new DatabaseHelper(this);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);

            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyValidation()) {
                    User user = databaseHelper.queryUser(editStudNumber.getText().toString(), editPassword.getText().toString());
                    if (user != null) {
                        Bundle mBundle = new Bundle();
                        mBundle.putString("user", String.valueOf(user.isLecturer()));
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Welcome " + user.getStudNumber(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                        editPassword.setText("");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private boolean emptyValidation() {
        if (TextUtils.isEmpty(editStudNumber.getText().toString()) || TextUtils.isEmpty(editPassword.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }
}
