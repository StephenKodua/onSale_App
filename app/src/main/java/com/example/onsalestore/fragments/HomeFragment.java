package com.example.onsalestore.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class HomeFragment extends Fragment {

    private RecyclerView rvClothingFeed;
    protected ClothingItemAdapter itemAdapter;
    protected List<ClothingItem> allItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvClothingFeed = view.findViewById(R.id.rvClothingPost);
        allItems = new ArrayList<>();
        itemAdapter = new ClothingItemAdapter(getContext(), allItems);
        rvClothingFeed.setAdapter(itemAdapter);
        rvClothingFeed.setLayoutManager(new GridLayoutManager(getContext(), 3));
        requestNikeItems();

        return view;
    }

    private void requestNikeItems() {
        String nikeApiEndpoint = "https://nike-products.p.rapidapi.com/shoes";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestHeaders headers = new RequestHeaders();
        headers.put("X-RapidAPI-Key", "2466bdbd3emsh59aaea237b7c6d5p13be08jsne6c71d3803ce");
        headers.put("X-RapidAPI-Host", "nike-products.p.rapidapi.com");

        allItems.clear(); //clear previous items before requesting new

        client.get(nikeApiEndpoint, headers, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONArray jsonArray = json.jsonArray;

                try {
                    List<ClothingItem> items = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        String productImageUrl = jsonObject.getString("img");
                        String productSource = jsonObject.getString("source");
                        String productName = jsonObject.getString("title");
                        String productPrice = jsonObject.getString("price");
                        String productUrl = jsonObject.getString("url");
                        ClothingItem productItem = new ClothingItem(productName, productImageUrl, productPrice, productSource, productUrl, null);
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
                throwable.printStackTrace();
            }
        });
    }
}
