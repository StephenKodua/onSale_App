package com.example.onsalestore.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.transition.Scene;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.onsale.R;

import org.parceler.Parcels;

public class IntroductoryActivity extends AppCompatActivity {

    ImageView logo, splashImg;
    TextView appName;
    LottieAnimationView lottieAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_introductory);
        final View imgContainerView = findViewById(R.id.logo_1);
        logo = findViewById(R.id.logo_1);
        splashImg = findViewById(R.id.img);
        appName = findViewById(R.id.appName);
        lottieAnimationView = findViewById(R.id.lottie);

        splashImg.animate().translationY(-100).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);


        Intent intent = new Intent(this, AuthenticationActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(IntroductoryActivity.this, (View) imgContainerView, "main_logo");
        startActivity(intent, options.toBundle());


        lottieAnimationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(IntroductoryActivity.this, (View) imgContainerView, "main_logo");
                startActivity(intent, options.toBundle());
            }
        });
    }

}