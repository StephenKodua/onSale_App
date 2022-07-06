package com.example.onsalestore.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.onsale.R;
import com.example.onsalestore.activities.AuthenticationActivity;
import com.example.onsalestore.activities.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {
    //Google logout contents
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;

    Button btnSignOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //Google logout contents
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);

        btnSignOut = view.findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //log out from parse if user sign through parse
                if (ParseUser.getCurrentUser() != null) {
                    ParseUser.logOutInBackground(new LogOutCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                returnToAuthentication();
                            } else {
                                Toast.makeText(getContext(), "Error signing out", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            returnToAuthentication();
                        }
                    });
                }
            }
        });
        return view;
    }

    private void returnToAuthentication() {
        startActivity(new Intent(getContext(), AuthenticationActivity.class));
    }
}