package com.example.onsalestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onsale.R;
import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //UI contents
    private TextView tvDeals, tvBuyAndSell;
    private ImageView imgMain;
    private Boolean firstImageDisplaying = false; //determine if a certain image is displaying

    //categories variables
    private RecyclerView rvHorizontal;
    protected CategoryAdapter categoryAdapter;
    protected ArrayList<Category> allCategories;

    //store variables
    private RecyclerView rvStoreHorizontal;
    protected StoreAdapter storesAdapter;
    protected ArrayList<Store> allStores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI contents
        tvDeals = findViewById(R.id.tvDeals);
        tvBuyAndSell = findViewById(R.id.tvBuyAndSell);
        imgMain = findViewById(R.id.imgMain);

        imgMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (firstImageDisplaying) {
                    imgMain.setImageResource(R.drawable.kids);
                    firstImageDisplaying = false;

                } else {
                    imgMain.setImageResource(R.drawable.shopping);
                    firstImageDisplaying = true;
                }
                return false;
            }
        });


        //categories
        rvHorizontal = findViewById(R.id.rvHorizontal);
        allCategories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this, allCategories);
        LinearLayoutManager categoriesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvHorizontal = (RecyclerView) findViewById(R.id.rvHorizontal);
        rvHorizontal.setLayoutManager(categoriesLayoutManager);
        rvHorizontal.setAdapter(categoryAdapter);
        categoriesQueryPosts();

        //stores
        rvStoreHorizontal = findViewById(R.id.rvStoreHorizontal);
        allStores = new ArrayList<>();
        storesAdapter = new StoreAdapter(this, allStores);
        LinearLayoutManager storesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvStoreHorizontal = (RecyclerView) findViewById(R.id.rvStoreHorizontal);
        rvStoreHorizontal.setLayoutManager(storesLayoutManager);
        rvStoreHorizontal.setAdapter(storesAdapter);
        storesQueryPosts();
    }

    private void categoriesQueryPosts() {
        ParseQuery<Category> query = ParseQuery.getQuery(Category.class);
        query.findInBackground(new FindCallback<Category>() {
            @Override
            public void done(List<Category> categories, ParseException e) {
                if (e != null) {
                    return;
                }
                // save received posts to list and notify adapter of new data
                allCategories.addAll(categories);
                categoryAdapter.notifyDataSetChanged();
            }
        });
    }

    private void storesQueryPosts() {
        ParseQuery<Store> query = ParseQuery.getQuery(Store.class);
        query.findInBackground(new FindCallback<Store>() {
            @Override
            public void done(List<Store> stores, ParseException e) {
                if (e != null) {
                    return;
                }
                allStores.addAll(stores);
                storesAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //execute if menu item is tapped
        if (item.getItemId() == R.id.signOutItem) {
            ParseUser.logOutInBackground(new LogOutCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        returnToAuthentication();
                    } else {
                        Toast.makeText(MainActivity.this, "Error signing out", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return true;
    }

    //Launch Login Fragment
    private void returnToAuthentication(){
        startActivity(new Intent(this, AuthenticationActivity.class));
    }
}