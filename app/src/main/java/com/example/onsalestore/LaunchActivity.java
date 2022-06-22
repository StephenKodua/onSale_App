package com.example.onsalestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.onsale.R;

public class LaunchActivity extends AppCompatActivity {

    Button btnLogin, btnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        btnLogin = findViewById(R.id.btnLogin);
        btnSign = findViewById(R.id.btnSign);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLoginActivity();
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSignActivity();
            }
        });
    }

    //Launch Login Activity
    private void goLoginActivity() {
        Intent i = new Intent (this, LoginActivity.class);
        startActivity(i);
    }

    //Launch Signup Activity
    private void goSignActivity() {
        Intent i = new Intent (this, SignupActivity.class);
        startActivity(i);
    }


}