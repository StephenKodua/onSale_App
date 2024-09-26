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
        String nikeApiEndpoint = "https://shoes-collections.p.rapidapi.com/shoes";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestHeaders headers = new RequestHeaders();
        headers.put("X-RapidAPI-Key","aa616bd4fdmshf4e3b2558774cdcp18d333jsnab818655e853");
        headers.put("X-RapidAPI-Host", "shoes-collections.p.rapidapi.com");

        allItems.clear(); //clear previous items before requesting new

        client.get(nikeApiEndpoint, headers, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONArray jsonArray = json.jsonArray;

                if (jsonArray == null) {
                    return;
                }

                try {
                    List<ClothingItem> items = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        String productImageUrl = jsonObject.getString("image");
                        //String productSource = jsonObject.getString("source");
                        String productName = jsonObject.getString("name");
                        String productPrice = "$" + jsonObject.getString("price");
                        //String productUrl = jsonObject.getString("url");
                        ClothingItem productItem = new ClothingItem(productName, productImageUrl, productPrice, null, null, null);
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
