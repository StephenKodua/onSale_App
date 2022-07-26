package com.example.onsalestore.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.onsale.R;
import com.example.onsalestore.adapters.CommentAdapter;
import com.example.onsalestore.objects.CommentItem;
import com.example.onsalestore.objects.PostItem;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {
    private RecyclerView rvPostComments;
    protected CommentAdapter commentAdapter;
    protected List<CommentItem> allComments;
    private ImageView postDetailImage;
    private ImageView postDetailShare;
    private TextView postDetailUserName;
    private TextView postDetailNumberOfLikes;
    private TextView postDetailNumberOfComments;
    private String objectId;
    private PostItem postItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        //TODO: set Post name,update class attribute abd parse
        setContentView(R.layout.activity_post_detail);

        postDetailImage = findViewById(R.id.postDetailImage);
        postDetailUserName = findViewById(R.id.postDetailUserName);
        postDetailNumberOfLikes = findViewById(R.id.postDetailNumberOfLikes);
        postDetailNumberOfComments = findViewById(R.id.postDetailNumberOfComments);
        postDetailShare = findViewById(R.id.postDetailShare);
        rvPostComments = findViewById(R.id.rvPostComments);
        allComments = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, allComments);
        rvPostComments.setAdapter(commentAdapter);
        rvPostComments.setLayoutManager(new LinearLayoutManager(this));
        rvPostComments.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        postDetailShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "mycloset://post" + "/" + postItem.getObjectId());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        PostItem extra_item = Parcels.unwrap(getIntent().getParcelableExtra("EXTRA_ITEM"));

        if (extra_item != null) {
            postItem = extra_item;
            String postImage = postItem.getItemImageUrl();

            if (postImage != null) {
                Glide.with(this).load(postImage).into(postDetailImage);
            }
            postDetailUserName.setText(postItem.getUser().getUsername());
            JSONArray jsonArrayLikeUsers = postItem.getJSONArray("likeUserNames");
            postDetailNumberOfLikes.setText(Integer.toString(jsonArrayLikeUsers.length()));
            JSONArray jsonArray = postItem.getJSONArray("comments");
            if (jsonArray == null) {
                postDetailNumberOfComments.setText("0");
            } else {
                postDetailNumberOfComments.setText(Integer.toString(jsonArray.length()));
            }
        } else {
            Intent intent = getIntent();
            Uri data = intent.getData();
            String stringUri;
            stringUri = data.toString();
            objectId = stringUri.substring(stringUri.length() - 10);
            ParseQuery<PostItem> query = ParseQuery.getQuery(PostItem.class);
            query.getInBackground(objectId, new GetCallback<PostItem>() {
                @Override
                public void done(PostItem object, ParseException e) {
                    String imageUrl = object.getItemImageUrl();
                    String username = object.getUser().getUsername();
                    Integer numberOfLikes = object.getNumberOfLikes();
                    postDetailUserName.setText(username);
                    postDetailNumberOfLikes.setText(numberOfLikes.toString());
                    JSONArray jsonArray = postItem.getJSONArray("comments");
                    if (jsonArray == null) {
                        postDetailNumberOfComments.setText("0");
                    } else {
                        postDetailNumberOfComments.setText(Integer.toString(jsonArray.length()));
                    }
                    if (imageUrl != null) {
                        Glide.with(PostDetailActivity.this).load(imageUrl).into(postDetailImage);
                    }
                }
            });
        }

        queryComments();
    }


    private void queryComments() {
        PostItem postItem = Parcels.unwrap(getIntent().getParcelableExtra("EXTRA_ITEM"));
        JSONArray jsonArray = postItem.getJSONArray("comments");

        if (jsonArray == null) {
            return;
        }
        for (int i = 0; i < jsonArray.length(); ++i) {
            try {
                JSONObject comment = (JSONObject) jsonArray.get(i);
                String commentID = (String) comment.get("objectId");
                queryComment(commentID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void queryComment(String commentId) {
        ParseQuery<CommentItem> query = ParseQuery.getQuery(CommentItem.class);
        query.addDescendingOrder("createdAt");
        query.getInBackground(commentId, new GetCallback<CommentItem>() {
            @Override
            public void done(CommentItem object, ParseException e) {
                if (e == null) {
                    allComments.add(object);
                    commentAdapter.notifyItemInserted(allComments.size() - 1);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

}