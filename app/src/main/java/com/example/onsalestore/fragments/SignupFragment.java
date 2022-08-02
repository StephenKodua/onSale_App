package com.example.onsalestore.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.onsale.R;
import com.example.onsalestore.activities.MainActivity;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignupFragment extends Fragment {

    private Button SignUpButton;
    private EditText etSignUpUsername, etSignUpPassword, etConfirmUpPassword;
    float v = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        etSignUpUsername = view.findViewById(R.id.etSignUpUsername);
        etSignUpPassword = view.findViewById(R.id.etSignUpPassword);
        etConfirmUpPassword = view.findViewById(R.id.etConfirmUpPassword);
        SignUpButton = view.findViewById(R.id.SignUpButton);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser user = new ParseUser();
                String username = etSignUpUsername.getText().toString();
                String password = etSignUpPassword.getText().toString();
                String passwordConfirmationInput = etConfirmUpPassword.getText().toString();
                if (password.equals(passwordConfirmationInput)) {
                    user.setUsername(username);
                    user.setPassword(password);
                } else {
                    //TODO
                    // Take appropriate action
                    etConfirmUpPassword.setError("Password does not match");
                }
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            goToMainActivity();
                        } else {
                            //TODO
                            // Account for all possible exception,
                            // example: if username already exist
                        }
                    }
                });
            }
        });


        etSignUpUsername.setTranslationX(800);
        etSignUpPassword.setTranslationX(800);
        etConfirmUpPassword.setTranslationX(800);
        SignUpButton.setTranslationX(800);

        etSignUpUsername.setAlpha(v);
        etSignUpPassword.setAlpha(v);
        etConfirmUpPassword.setAlpha(v);
        SignUpButton.setAlpha(v);

        etSignUpUsername.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        etSignUpPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        etConfirmUpPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        SignUpButton.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


        return view;
    }

    private void goToMainActivity() {
        startActivity(new Intent(getContext(), MainActivity.class));
    }
}

