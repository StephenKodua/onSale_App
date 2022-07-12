package com.example.onsalestore.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onsale.R;
import com.example.onsalestore.objects.ClosetItem;
import com.example.onsalestore.objects.ClothingItem;
import com.example.onsalestore.objects.PostItem;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class ClosetItemAdapter extends RecyclerView.Adapter<ClosetItemAdapter.ViewHolder> {
    private Context context;
    private List<ClosetItem> closetItemList;

    public ClosetItemAdapter(Context context, List<ClosetItem> closetItemList) {
        this.context = context;
        this.closetItemList = closetItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_closet_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClosetItem closetItem = closetItemList.get(position);
        holder.bind(closetItem);
    }

    @Override
    public int getItemCount() {
        return closetItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvClosetItemName;
        private ImageView ivClosetItemImage, ivPostImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClosetItemName = itemView.findViewById(R.id.tvClosetItemName);
            ivClosetItemImage = itemView.findViewById(R.id.ivClosetItemImage);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);

            ivPostImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostItem postItem = new PostItem(closetItemList.get(getPosition()));
                    savePost(postItem.getItemImageUrl(), postItem.getUser());
                }
            });
        }

        private void savePost(String postImageUrl, ParseUser currentUser) {
            PostItem postItem = new PostItem(closetItemList.get(getPosition()));
            postItem.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.e("ClosetItemAdapter", "Error while saving", e);
                        Toast.makeText(context.getApplicationContext(), "Error while saving!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Log.i("ClosetItemAdapter", "Post save was successful!");
                    Toast.makeText(context.getApplicationContext(), "Item posted successfully!", Toast.LENGTH_LONG).show();
                    postItem.setUser();
                    postItem.setItemImageUrl(postItem.getItemImageUrl());
                    currentUser.add("posts", postItem);
                    currentUser.saveInBackground();

                }
            });
        }

        public void bind(ClosetItem item) {
            Log.d("ClosetItemAdapter", item.getItemName());
            tvClosetItemName.setText(item.getItemName());
            String image = item.getItemImageUrl();
            if (image != null) {
                Glide.with(context).load(image).into(ivClosetItemImage);
            }
        }
    }
}
