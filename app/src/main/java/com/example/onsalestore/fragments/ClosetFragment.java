package com.example.onsalestore.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onsale.R;
import com.example.onsalestore.adapters.ClosetItemAdapter;

import com.example.onsalestore.objects.ClosetItem;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClosetFragment extends Fragment {

    private RecyclerView rvClosetItems;
    protected ClosetItemAdapter closetItemAdapter;
    protected List<ClosetItem> allClosetItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet, container, false);

        rvClosetItems = view.findViewById(R.id.rvClosetItems);
        allClosetItems = new ArrayList<>();
        closetItemAdapter = new ClosetItemAdapter(getContext(), allClosetItems);
        rvClosetItems.setAdapter(closetItemAdapter);
        rvClosetItems.setLayoutManager(new LinearLayoutManager(getContext()));
        populateClosetItemsList();

        return view;
    }

    private void populateClosetItemsList() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        JSONArray itemsInCloset = currentUser.getJSONArray("closet");
        if (itemsInCloset == null) {
            return;
        }
        for (int i = 0; i < itemsInCloset.length(); ++i) {
            try {
                JSONObject closetItem = (JSONObject) itemsInCloset.get(i);
                String itemId = (String) closetItem.get("objectId");
                queryClosetItems(itemId);
            } catch (Exception e) {
                //TODO: Decide how to handle this exception later
            }
        }
    }

    private void queryClosetItems(String itemId) {
        ParseQuery<ClosetItem> query = ParseQuery.getQuery(ClosetItem.class);
        query.addDescendingOrder("createdAt");
        query.getInBackground(itemId, new GetCallback<ClosetItem>() {
            @Override
            public void done(ClosetItem object, ParseException e) {
                if (e == null) {
                    allClosetItems.add(object);
                    closetItemAdapter.notifyDataSetChanged();
                } else {
                    //TODO: Decide how to handle this exception later
                }
            }
        });
    }
}