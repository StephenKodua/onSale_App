package com.example.onsalestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.onsale.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {
    private EditText signupUsername,signupPassword,PasswordConfirm;
    private Button btnMainSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupUsername = findViewById(R.id.signupUsername);
        signupPassword = findViewById(R.id.signupPassword);
        PasswordConfirm = findViewById(R.id.PasswordConfirm);
        btnMainSign = findViewById(R.id.btnMainSign);

        btnMainSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                // Request user email and account for email confirmation
                ParseUser user = new ParseUser();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();
                String passwordconfirm = PasswordConfirm.getText().toString();

                if (password.equals(passwordconfirm)){
                    user.setUsername(username);
                    user.setPassword(password);
                }
                else{
                    //TODO
                    // Take appropriate action
                    return;
                }

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            goLoginActivity();
                        } else {
                            //TODO
                            // Account for all possible exception,
                            // example: if username already exist
                        }
                    }
                });
            }
        });
    }

    //Launches loginActivity
    private void goLoginActivity() {
        Intent i = new Intent (this, LoginActivity.class);
        startActivity(i);
    }
}