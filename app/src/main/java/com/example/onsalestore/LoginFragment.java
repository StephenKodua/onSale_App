package com.example.onsalestore;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onsale.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginFragment extends Fragment {

    private Button btnLogin, btnNewUser;
    private EditText etLoginUsername, etLoginPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        etLoginUsername =view.findViewById(R.id.etLoginUsername);
        etLoginPassword = view.findViewById(R.id.etLoginPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnNewUser = view.findViewById(R.id.btnNewUser);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etLoginUsername.getText().toString();
                String password = etLoginPassword.getText().toString();
                loginUser(username, password);
            }
        });

        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new SignupFragment()).commit();
            }
        });
        return view;
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
                goToMainActivity();
            }
        });
    }

    //Launch MainActivity
    private void goToMainActivity() {
        startActivity(new Intent(getContext(), MainActivity.class));
    }
}



