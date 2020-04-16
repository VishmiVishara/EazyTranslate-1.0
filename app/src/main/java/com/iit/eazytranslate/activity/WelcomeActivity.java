package com.iit.eazytranslate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iit.eazytranslate.R;


public class WelcomeActivity extends AppCompatActivity {

    private Button btnSignUp;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initializeUIComponents();
        initializeListeners();
    }
    private void initializeUIComponents() {
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);
    }


    private void initializeListeners() {

        // Sign up button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
//                startActivity(intent);
            }
        });


        // Login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
            }
        });

    }
}