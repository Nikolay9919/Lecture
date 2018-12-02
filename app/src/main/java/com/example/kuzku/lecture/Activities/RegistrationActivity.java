package com.example.kuzku.lecture.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kuzku.lecture.Database.DatabaseHelper;
import com.example.kuzku.lecture.Models.User;
import com.example.kuzku.lecture.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editStudNumber;
    private EditText editPassword;
    private CheckBox isLecturerCheck;
    private Button registrationButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        editStudNumber = findViewById(R.id.stud_number_input);
        editPassword = findViewById(R.id.password_input);
        isLecturerCheck = findViewById(R.id.is_Lecturer);
        registrationButton = findViewById(R.id.registration_button);
        isLecturerCheck.setVisibility(View.INVISIBLE);
        dbHelper = new DatabaseHelper(this);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyValidation()) {
                    dbHelper.addUser(new User(editStudNumber.getText().toString(), editPassword.getText().toString(), false));
                    Toast.makeText(RegistrationActivity.this, "Added User", Toast.LENGTH_SHORT).show();
                    editPassword.setText("");
                    editStudNumber.setText("");
                    goHome();
                } else if (!emptyValidation()) {
                    Toast.makeText(RegistrationActivity.this, "Empty fields", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(RegistrationActivity.this, "Error", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void goHome() {

        dbHelper.close();

        Intent intent = new Intent(this, Main2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private boolean emptyValidation() {
        if (TextUtils.isEmpty(editStudNumber.getText().toString()) || TextUtils.isEmpty(editPassword.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

}