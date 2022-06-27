package com.example.onsalestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //UI contents
    private TextView tvDeals, tvBuyandsell;
    private ImageView imgMain;
    private Boolean FirstImageDisplaying = false; //determine if a certain image is displaying

    //categories variables
    private RecyclerView rvHorizontal;
    protected CategoryAdapter adapter;
    protected ArrayList<Categories> allCategories;

    //store variables
    private RecyclerView rvStoreHorizontal;
    protected StoresAdapter storesadapter;
    protected ArrayList<Stores> allStores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI contents
        tvDeals = findViewById(R.id.tvDeals);
        tvBuyandsell = findViewById(R.id.tvBuyandSell);
        imgMain = findViewById(R.id.imgMain);

        imgMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (FirstImageDisplaying) {
                    imgMain.setImageResource(R.drawable.kids);
                    FirstImageDisplaying = false;

                } else {
                    imgMain.setImageResource(R.drawable.shopping);
                    FirstImageDisplaying = true;
                }
                return false;
            }
        });


        //categories
        rvHorizontal = findViewById(R.id.rvHorizontal);
        allCategories = new ArrayList<>();
        adapter = new CategoryAdapter(this, allCategories);
        LinearLayoutManager CategoriesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvHorizontal = (RecyclerView) findViewById(R.id.rvHorizontal);
        rvHorizontal.setLayoutManager(CategoriesLayoutManager);
        rvHorizontal.setAdapter(adapter);
        CategoriesQueryPosts();

        //stores
        rvStoreHorizontal = findViewById(R.id.rvStoreHorizontal);
        allStores = new ArrayList<>();
        storesadapter = new StoresAdapter(this, allStores);
        LinearLayoutManager StoresLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvStoreHorizontal = (RecyclerView) findViewById(R.id.rvStoreHorizontal);
        rvStoreHorizontal.setLayoutManager(StoresLayoutManager);
        rvStoreHorizontal.setAdapter(storesadapter);
        StoresQueryPosts();
    }

    private void CategoriesQueryPosts() {
        ParseQuery<Categories> query = ParseQuery.getQuery(Categories.class);
        query.findInBackground(new FindCallback<Categories>() {
            @Override
            public void done(List<Categories> categories, ParseException e) {
                if (e != null) {
                    return;
                }
                // save received posts to list and notify adapter of new data
                allCategories.addAll(categories);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void StoresQueryPosts() {
        ParseQuery<Stores> query = ParseQuery.getQuery(Stores.class);
        query.findInBackground(new FindCallback<Stores>() {
            @Override
            public void done(List<Stores> stores, ParseException e) {
                if (e != null) {
                    return;
                }
                allStores.addAll(stores);
                storesadapter.notifyDataSetChanged();
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
                        goToLoginFragment();
                    } else {
                        Toast.makeText(MainActivity.this, "Error signing out", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return true;
    }

    //Launch Login Fragment
    private void goToLoginFragment() {
        startActivity(new Intent(this, MainActivityFragment.class));
    }
}