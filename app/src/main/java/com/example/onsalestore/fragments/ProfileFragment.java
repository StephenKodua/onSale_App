package com.example.onsalestore.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onsale.R;
import com.example.onsalestore.activities.AuthenticationActivity;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {

    Button btnSignOut;
    TextView userName;
    TextView numberOfPost;
    TextView numberOfClosetItems;
    ImageView userProfileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile_item, container, false);

        userName = view.findViewById(R.id.profileUserName);
        numberOfPost = view.findViewById(R.id.numberOfPost);
        numberOfClosetItems = view.findViewById(R.id.numberOfClosetItem);
        userProfileImage = view.findViewById(R.id.postItemProfileImage);
        btnSignOut = view.findViewById(R.id.btnSignOut);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ParseUser.getCurrentUser() != null) {
                    ParseUser.logOutInBackground(new LogOutCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                returnToAuthentication();
                            } else {
                                Toast.makeText(getContext(), "Error signing out", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        ParseUser currentUser = ParseUser.getCurrentUser();
        String username = currentUser.getString("username");
        Integer integerPost = currentUser.getJSONArray("posts").length();
        Integer integerClosetItems = currentUser.getJSONArray("closet").length();
        userName.setText(username);
        numberOfPost.setText(integerPost.toString());
        numberOfClosetItems.setText(integerClosetItems.toString());
        return view;
    }

    private void returnToAuthentication() {
        startActivity(new Intent(getContext(), AuthenticationActivity.class));
    }
}