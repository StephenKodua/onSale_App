package com.example.onsalestore.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onsale.R;
import com.example.onsalestore.fragments.ClosetItemMultiSelectListener;
import com.example.onsalestore.objects.ClosetItem;
import com.example.onsalestore.objects.PostItem;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClosetItemAdapter extends RecyclerView.Adapter<ClosetItemAdapter.ViewHolder> {
    private Context context;
    private List<ClosetItem> closetItemList;
    private Set<String> selectedItems = new HashSet<>();
    private ClosetItemMultiSelectListener clickListener;

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
        holder.closetItemCardView.startAnimation(AnimationUtils.loadAnimation(holder.closetItemCardView.getContext(),
                R.anim.anim_sliding));
    }

    @Override
    public int getItemCount() {
        return closetItemList.size();
    }

    public void setItemClickListener(ClosetItemMultiSelectListener clickListener) {
        this.clickListener = clickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvClosetItemName;
        private ImageView ivClosetItemImage;
        private ImageView ivPostImage;
        private ImageView checkBox;
        private CardView closetItemCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClosetItemName = itemView.findViewById(R.id.tvClosetItemName);
            ivClosetItemImage = itemView.findViewById(R.id.ivClosetItemImage);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            checkBox = itemView.findViewById(R.id.checkBox);
            closetItemCardView = itemView.findViewById(R.id.closetItemCardView);


            ivPostImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostItem postItem = new PostItem(closetItemList.get(getPosition()));
                    savePost(postItem);
                }
            });
        }

        private void savePost(PostItem postItem) {
            postItem.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Toast.makeText(context.getApplicationContext(), "Error while saving!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(context.getApplicationContext(), "Item posted successfully!", Toast.LENGTH_LONG).show();
                    postItem.setUser();
                    postItem.setItemImageUrl(postItem.getItemImageUrl());
                }
            });
        }

        public void bind(ClosetItem item) {
            checkBox.setVisibility(selectedItems.contains(item.getObjectId()) ? View.VISIBLE : View.GONE);
            tvClosetItemName.setText(item.getItemName());
            String image = item.getItemImageUrl();
            if (image != null) {
                Glide.with(context).load(image).into(ivClosetItemImage);
            }

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View view) {
                    clickItem(item);
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!selectedItems.isEmpty()) {
                        clickItem(item);
                    }
                }
            });
        }

        private void clickItem(ClosetItem item) {
            if (!selectedItems.contains(item.getObjectId())) {
                checkBox.setVisibility(View.VISIBLE);
                ivClosetItemImage.setBackgroundColor(Color.LTGRAY);
                selectedItems.add(item.getObjectId());

            } else {
                checkBox.setVisibility(View.GONE);
                selectedItems.remove(item.getObjectId());
            }
            if (clickListener != null) {
                clickListener.onMultiSelectUpdated(selectedItems.size());
            }
        }
    }
}
