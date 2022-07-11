package com.example.onsalestore.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.onsale.R;
import com.example.onsalestore.activities.MainActivity;
import com.example.onsalestore.activities.MainActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.json.JSONException;


public class LoginFragment extends Fragment {

    //Main login contents
    private Button btnLaunchLogin;
    private EditText etLoginUsername, etLoginPassword;

    //Google login contents
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;
    private ImageView ivGoogleLogin;
    private static final int REQUEST_CODE = 12000;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //Main login contents
        etLoginUsername = view.findViewById(R.id.etLoginUsername);
        etLoginPassword = view.findViewById(R.id.etLoginPassword);
        btnLaunchLogin = view.findViewById(R.id.btnLaunchLogin);


        //Google login contents
        ivGoogleLogin = view.findViewById(R.id.ivGoogleLogin);
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);


        //sign in with google
        ivGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

        //sign in with parse
        btnLaunchLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etLoginUsername.getText().toString();
                String password = etLoginPassword.getText().toString();
                loginUser(username, password);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == REQUEST_CODE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

//GOOGLE SIGN IN FLOW
//fire sign in intent to start sign-in flow
    private void googleSignIn() {
        Intent googleSignInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(googleSignInIntent, REQUEST_CODE);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            //object contains information about the signed-in user, such as the user's name.
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            goToMainActivity();

        } catch (ApiException e) {
            Log.w("LoginFragment", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    //parse
    private void loginUser(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    //TODO take right action
                    // for example notify user or send user to login again
                    Log.i("LoginFragment", "This is the error that is being thrown : ", e);
                    return;
                }
                goToMainActivity();
            }
        });
    }

    //Launch MainActivity
    private void goToMainActivity() {
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
    }
}

