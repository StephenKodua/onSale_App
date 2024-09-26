package com.example.onsalestore.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onsale.R;
import com.example.onsalestore.activities.WriteCommentActivity;
import com.example.onsalestore.activities.PostDetailActivity;
import com.example.onsalestore.objects.ClothingItem;
import com.example.onsalestore.objects.CommentItem;
import com.example.onsalestore.objects.PostItem;
import com.parse.Parse;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context context;
    private List<CommentItem> commentList;

    public CommentAdapter(Context context, List<CommentItem> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_comment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentItem commentItem = commentList.get(position);
        holder.bind(commentItem);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCommentUserName, tvDetailComment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCommentUserName = itemView.findViewById(R.id.tvCommentUserName);
            tvDetailComment = itemView.findViewById(R.id.tvDetailComment);
        }

        public void bind(CommentItem item) {
            String commentText = item.getString("comment");
            ParseUser username = (ParseUser) item.getParseUser("user");
            String commentPoster = username.getString("username");
            tvDetailComment.setText(commentText);
            tvCommentUserName.setText(commentPoster);
        }
    }
}
