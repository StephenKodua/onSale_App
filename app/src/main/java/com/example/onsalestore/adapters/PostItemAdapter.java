package com.example.onsalestore.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.onsalestore.objects.PostItem;

import org.parceler.Parcels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostItemAdapter extends RecyclerView.Adapter<PostItemAdapter.ViewHolder> {
    private Context context;
    private List<PostItem> postItemList;


    public PostItemAdapter(Context context, List<PostItem> postItemList) {
        this.context = context;
        this.postItemList = postItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostItem postItem = postItemList.get(position);
        holder.bind(postItem);
    }

    @Override
    public int getItemCount() {
        return postItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //closet items
        private TextView tvUserName, numberOfLikes;
        private ImageView userProfileImage, ivUserPost, ivComment;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            numberOfLikes = itemView.findViewById(R.id.numberOfLikes);
            userProfileImage = itemView.findViewById(R.id.userProfileImage);
            ivUserPost = itemView.findViewById(R.id.ivUserPost);
            ivComment = itemView.findViewById(R.id.ivComment);

            ivUserPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    PostItem postItem = postItemList.get(position);
                    Intent intent = new Intent(itemView.getContext(), PostDetailActivity.class);
                    intent.putExtra("EXTRA_ITEM", Parcels.wrap(postItem));
                    itemView.getContext().startActivity(intent);
                }
            });

            ivComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    PostItem postItem = postItemList.get(position);
                    Intent intent = new Intent(itemView.getContext(), WriteCommentActivity.class);
                    intent.putExtra("EXTRA_ITEM", Parcels.wrap(postItem));
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        public void bind(PostItem item) {
            tvUserName.setText(item.getUser().getUsername());
            numberOfLikes.setText(item.getNumberOfLikes().toString());
            String image = item.getItemImageUrl();
            if (image != null) {
                Glide.with(context).load(image).into(ivUserPost);
            }
        }
    }
}
