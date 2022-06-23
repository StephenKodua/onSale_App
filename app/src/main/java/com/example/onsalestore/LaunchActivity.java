package com.example.onsalestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.onsale.R;
import com.parse.ParseUser;

public class LaunchActivity extends AppCompatActivity {

    private Button btnLogin, btnSign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Checks if user already logged in
        if (ParseUser.getCurrentUser() != null){
            goToMainActivity();
        }
        setContentView(R.layout.activity_launch);
        btnLogin = findViewById(R.id.btnLogin);
        btnSign = findViewById(R.id.btnSign);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginActivity();
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignActivity();
            }
        });
    }

    //Launch Login Activity
    private void goToLoginActivity() {
        startActivity(new Intent (this, LoginActivity.class));
    }

    //Launch Signup Activity
    private void goToSignActivity() {
        startActivity(new Intent (this, SignupActivity.class));
    }

    //Launch MainActivity
    private void goToMainActivity() {
        startActivity(new Intent (this, MainActivity.class));
    }


}