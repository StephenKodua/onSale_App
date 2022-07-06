package com.example.onsalestore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.onsale.R;
import com.example.onsalestore.adapters.ClothingItemAdapter;
import com.example.onsalestore.fragments.ClosetFragment;
import com.example.onsalestore.fragments.HomeFragment;
import com.example.onsalestore.fragments.ProfileFragment;
import com.example.onsalestore.fragments.ShareFragment;
import com.example.onsalestore.objects.ClothingItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        addFragment();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new HomeFragment();
                switch (item.getItemId()) {
                    case R.id.homeItem:
                        fragment = new HomeFragment();
                        break;
                    case R.id.shareItem:
                        fragment = new ShareFragment();
                        break;
                    case R.id.closetItem:
                        fragment = new ClosetFragment();
                        break;
                    case R.id.profileItem:
                        fragment = new ProfileFragment();
                        break;
                    default:
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.homeFragmentContainer, fragment).commit();
                return true;
            }
        });
    }

    public void addFragment() {
        HomeFragment fragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.homeFragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}