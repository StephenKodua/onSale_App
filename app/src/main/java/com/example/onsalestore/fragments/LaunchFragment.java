package com.example.onsalestore.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.onsale.R;
import com.example.onsalestore.activities.MainActivity;
import com.parse.ParseUser;


public class LaunchFragment extends Fragment {
    private Button btnLaunchLogin, btnLaunchSignup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ParseUser.getCurrentUser() != null){
            startActivity(new Intent(getContext(), MainActivity.class));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_launch, container, false);
        btnLaunchLogin = view.findViewById(R.id.btnLaunchLogin);
        btnLaunchSignup = view.findViewById(R.id.btnLaunchSignup);

        btnLaunchLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.loginFragmentContainer, new LoginFragment()).addToBackStack("tag").commit();
            }
        });

        btnLaunchSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.loginFragmentContainer, new SignupFragment()).addToBackStack("tag").commit();
            }
        });

        return view;
    }

}
