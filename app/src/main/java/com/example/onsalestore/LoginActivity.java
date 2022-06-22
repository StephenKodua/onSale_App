package com.example.onsalestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.onsale.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    private TextView loginText;
    private EditText etUsername, etPassword;
    private Button btnMainLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Checks if user already logged in
        if (ParseUser.getCurrentUser() != null){
            goMainActivity();
        }

        etUsername = findViewById(R.id.signupUsername);
        etPassword = findViewById(R.id.signupPassword);
        loginText = findViewById(R.id.LoginText);
        btnMainLogin = findViewById(R.id.btnMainLogin);

        btnMainLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });
    }

    private void loginUser(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null){
                    //TODO take right action
                    // for example notify user or send user to login again
                    return;
                }
                goMainActivity();
            }
        });
    }

    //Launch MainActivity
    private void goMainActivity() {
        Intent i = new Intent (this, MainActivity.class);
        startActivity(i);
    }
}