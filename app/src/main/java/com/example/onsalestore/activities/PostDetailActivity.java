package com.example.onsalestore.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("") ;
        //TODO: set Post name,update class attribute abd parse
        setContentView(R.layout.activity_post_detail);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        Uri data = intent.getData();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else {
                // Handle other intents, such as being started from the home screen
            }
        }

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

        PostItem postItem = Parcels.unwrap(getIntent().getParcelableExtra("EXTRA_ITEM"));
        String postImage = postItem.getItemImageUrl();

        if (postImage != null) {
            Glide.with(this).load(postImage).into(postDetailImage);
        }

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

        postDetailUserName.setText(postItem.getUser().getUsername());
        JSONArray jsonArray = postItem.getJSONArray("comments");
        Integer likes = postItem.getNumberOfLikes();
        postDetailNumberOfLikes.setText(Integer.toString(likes));
        if (jsonArray == null) {
            postDetailNumberOfComments.setText("0");
        } else {
            postDetailNumberOfComments.setText(Integer.toString(jsonArray.length()));
        }
        queryComments();
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
        }
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
        query.addAscendingOrder("createdAt");
        query.getInBackground(commentId, new GetCallback<CommentItem>() {
            @Override
            public void done(CommentItem object, ParseException e) {
                if (e == null) {
                    allComments.add(object);
                    commentAdapter.notifyItemInserted(allComments.size()-1);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}