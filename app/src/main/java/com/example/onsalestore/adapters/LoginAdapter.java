package com.example.onsalestore.adapters;


import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.onsalestore.fragments.LoginFragment;
import com.example.onsalestore.fragments.SignupFragment;

public class LoginAdapter extends FragmentPagerAdapter {

    private Context context;
    private int totalTabs;

    public LoginAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title = null;
        if (position == 0) {
            title = "Login";
        } else if (position == 1) {
            title = "Sign up";
        }
        return title;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LoginFragment loginFragment = new LoginFragment();
                return loginFragment;
            case 1:
                SignupFragment signupFragment = new SignupFragment();
                return signupFragment;
            default:
                return null;
        }
    }
}
