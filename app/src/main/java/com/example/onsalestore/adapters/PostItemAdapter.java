package com.example.onsalestore.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onsale.R;
import com.example.onsalestore.activities.WriteCommentActivity;
import com.example.onsalestore.activities.PostDetailActivity;
import com.example.onsalestore.objects.PostItem;

import org.json.JSONArray;
import org.parceler.Parcels;

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
        holder.postItemCardView.startAnimation(AnimationUtils.loadAnimation(holder.postItemCardView.getContext(),
                R.anim.anim_sliding));
    }

    @Override
    public int getItemCount() {
        return postItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUserName;
        private TextView numberOfLikes;
        private TextView numberOfComments;
        private ImageView postItemProfileImage;
        private ImageView ivUserPost;
        private ImageView postItemComment;
        private ImageView postItemLike;
        private ImageView postItemShare;
        private CardView postItemCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.postItemUserName);
            postItemProfileImage = itemView.findViewById(R.id.postItemProfileImage);
            ivUserPost = itemView.findViewById(R.id.postItemImage);
            numberOfLikes = itemView.findViewById(R.id.postItemNumberOfLikes);
            numberOfComments = itemView.findViewById(R.id.postItemNumberOfComments);
            postItemLike = itemView.findViewById(R.id.postItemLike);
            postItemComment = itemView.findViewById(R.id.postItemComment);
            postItemShare = itemView.findViewById(R.id.postItemShare);
            postItemCardView = itemView.findViewById(R.id.postItemCardView);

            ivUserPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    PostItem postItem = postItemList.get(position);
                    Intent intent = new Intent(itemView.getContext(), PostDetailActivity.class);
                    intent.putExtra("EXTRA_ITEM", Parcels.wrap(postItem));
                    context.startActivity(intent);
                }
            });

            postItemComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    PostItem postItem = postItemList.get(position);
                    Intent intent = new Intent(itemView.getContext(), WriteCommentActivity.class);
                    intent.putExtra("EXTRA_ITEM", Parcels.wrap(postItem));
                    context.startActivity(intent);
                }
            });
        }

        public void bind(PostItem item) {
            JSONArray jsonArray = item.getJSONArray("comments");
            Integer likes = item.getNumberOfLikes();
            numberOfLikes.setText(Integer.toString(likes));
            if (jsonArray == null) {
                numberOfComments.setText("0");
            } else {
                numberOfComments.setText(Integer.toString(jsonArray.length()));
            }
            tvUserName.setText(item.getUser().getUsername());
            String image = item.getItemImageUrl();
            if (image != null) {
                Glide.with(context).load(image).into(ivUserPost);
            }
        }
    }
}
