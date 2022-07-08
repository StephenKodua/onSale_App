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
import com.parse.ParseException;
import com.parse.ParseQuery;

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
        queryClosetItems();

        return view;
    }

    private void queryClosetItems() {
        ParseQuery<ClosetItem> query = ParseQuery.getQuery(ClosetItem.class);
        //query.include(ClosetItem.KEY_USER);
        query.findInBackground(new FindCallback<ClosetItem>() {
            @Override
            public void done(List<ClosetItem> stores, ParseException e) {
                if (e != null) {
                    return;
                }
                allClosetItems.addAll(stores);
                closetItemAdapter.notifyDataSetChanged();
            }
        });
    }
}