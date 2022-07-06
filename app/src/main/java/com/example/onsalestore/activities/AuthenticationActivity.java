package com.example.onsalestore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.onsale.R;
import com.example.onsalestore.fragments.LaunchFragment;
import com.example.onsalestore.fragments.LoginFragment;
import com.parse.ParseUser;

public class AuthenticationActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Checks if user already logged in
        if (ParseUser.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
        setContentView(R.layout.activity_authentication);
        addFragment();
    }

    public void addFragment() {
        LaunchFragment fragment = new LaunchFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.loginFragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}