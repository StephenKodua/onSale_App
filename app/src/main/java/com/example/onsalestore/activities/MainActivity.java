package com.example.onsalestore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.onsale.R;
import com.example.onsalestore.adapters.ClothingItemAdapter;
import com.example.onsalestore.objects.ClothingItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    private TextView tvClothingDeals;
    private RecyclerView rvClothingFeed;
    protected ClothingItemAdapter itemAdapter;
    protected List<ClothingItem> allItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvClothingDeals = findViewById(R.id.tvClothingDeals);
        rvClothingFeed = findViewById(R.id.rvClothingFeed);
        allItems = new ArrayList<>();
        itemAdapter = new ClothingItemAdapter(this, allItems);
        rvClothingFeed.setAdapter(itemAdapter);
        rvClothingFeed.setLayoutManager(new GridLayoutManager(this, 3));
        requestNikeItems();
    }

    private void requestNikeItems() {
        String nikeApiEndpoint = "https://nike-products.p.rapidapi.com/shoes";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestHeaders headers = new RequestHeaders();
        headers.put("X-RapidAPI-Key", "aa616bd4fdmshf4e3b2558774cdcp18d333jsnab818655e853");
        headers.put("X-RapidAPI-Host", "nike-products.p.rapidapi.com");

        allItems.clear(); //clear previous items before requesting new

        client.get(nikeApiEndpoint, headers, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("ItemActivity", "onSuccess");
                JSONArray jsonArray = json.jsonArray;

                try {
                    List<ClothingItem> items = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        String productImageUrl = jsonObject.getString("img");
                        String productSource = jsonObject.getString("source");
                        String productName = jsonObject.getString("title");
                        String productPrice = jsonObject.getString("price");

                        ClothingItem productItem = new ClothingItem(productName, productImageUrl, productPrice, productSource);
                        items.add(productItem);
                    }

                    allItems.addAll(items);
                    itemAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.homeItem:
                tvClothingDeals.setText("Clothing Deals");
                requestNikeItems();
                //TODO : Do something
            case R.id.shareItem:
                // TODO :
            case R.id.closetItem:
                //TODO :
            case R.id.profileItem:
                //TODO:
        }

        return true;
    }

    //Launch Login Fragment
    private void returnToAuthentication() {
        startActivity(new Intent(this, AuthenticationActivity.class));
    }


}