package com.example.onsalestore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.onsale.R;
import com.example.onsalestore.objects.ClothingItem;
import com.example.onsalestore.objects.CommentItem;
import com.example.onsalestore.objects.PostItem;
import com.parse.ParseUser;

import org.parceler.Parcels;

public class WriteCommentActivity extends AppCompatActivity {
    private EditText etComment;
    private ImageView postComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_write_comment);
        etComment = findViewById(R.id.etComment);
        postComment = findViewById(R.id.postComment);

        PostItem postItem = Parcels.unwrap(getIntent().getParcelableExtra("EXTRA_ITEM"));

        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = etComment.getText().toString();
                CommentItem commentItem = new CommentItem(comment);
                commentItem.saveInBackground();
                postItem.add("comments", commentItem);
                postItem.saveInBackground();
                etComment.getText().clear();
                finish();
            }
        });
    }
}