package com.example.onsalestore.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onsale.R;


import com.example.onsalestore.adapters.PostItemAdapter;

import com.example.onsalestore.objects.PostItem;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostFragment extends Fragment {

    private RecyclerView rvClothingPost;
    protected PostItemAdapter postItemAdapter;
    protected List<PostItem> allPostItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        rvClothingPost = view.findViewById(R.id.rvClothingPost);
        allPostItems = new ArrayList<>();
        postItemAdapter = new PostItemAdapter(getContext(), allPostItems);
        rvClothingPost.setAdapter(postItemAdapter);
        rvClothingPost.setLayoutManager(new LinearLayoutManager(getContext()));
        rvClothingPost.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        queryPosts();

        return view;
    }

    private void queryPosts() {
        ParseQuery<PostItem> query = ParseQuery.getQuery(PostItem.class);
        query.include(PostItem.KEY_USER);
        query.addAscendingOrder("createdAt");
        query.findInBackground(new FindCallback<PostItem>() {
            @Override
            public void done(List<PostItem> posts, ParseException e) {
                if (e != null) {
                    Log.e("ShareFragment", "Issue with getting posts", e);
                    return;
                }
                allPostItems.addAll(posts);
                postItemAdapter.notifyDataSetChanged();
            }
        });
    }
}