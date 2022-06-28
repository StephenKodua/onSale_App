package com.example.onsalestore;

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
import android.widget.Toast;

import com.example.onsale.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignupFragment extends Fragment {

    private Button btnSignup;
    private EditText etSignupUsername, etSignupPassword, etPasswordConfirmation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        etSignupUsername = view.findViewById(R.id.etSignupUsername);
        etSignupPassword = view.findViewById(R.id.etSignupPassword);
        etPasswordConfirmation = view.findViewById(R.id.etSignupPassword);
        btnSignup = view.findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser user = new ParseUser();
                String username = etSignupUsername.getText().toString();
                String password = etSignupPassword.getText().toString();
                String passwordConfirmationInput = etPasswordConfirmation.getText().toString();
                if (password.equals(passwordConfirmationInput)) {
                    user.setUsername(username);
                    user.setPassword(password);
                } else {
                    //TODO
                    // Take appropriate action
                    etPasswordConfirmation.setError("Password does not match");
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
        return view;
    }

    private void goToMainActivity() {
        startActivity(new Intent(getContext(), MainActivity.class));
    }
}

