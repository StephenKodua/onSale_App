package com.example.onsalestore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.onsale.R;
import com.example.onsalestore.adapters.CommentAdapter;
import com.example.onsalestore.objects.CommentItem;
import com.example.onsalestore.objects.PostItem;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {
    private RecyclerView rvPostComments;
    protected CommentAdapter commentAdapter;
    protected List<CommentItem> allComments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        rvPostComments = findViewById(R.id.rvPostComments);
        allComments = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, allComments);
        rvPostComments.setAdapter(commentAdapter);
        rvPostComments.setLayoutManager(new LinearLayoutManager(this));
        rvPostComments.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
                Log.d("PostDetailActivity", "Inside the try block");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void queryComment(String commentId) {
        ParseQuery<CommentItem> query = ParseQuery.getQuery(CommentItem.class);
        query.getInBackground(commentId, new GetCallback<CommentItem>() {
            @Override
            public void done(CommentItem object, ParseException e) {
                if (e == null) {
                    allComments.add(object);
                    String comment = (String) object.getString("comment");
                    Log.d("PostDetailActivity","Comment : " + comment);
                    commentAdapter.notifyItemInserted(allComments.size());
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}