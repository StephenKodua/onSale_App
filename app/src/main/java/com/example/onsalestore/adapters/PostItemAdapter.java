package com.example.onsalestore.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.GestureDetector;
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
import com.example.onsalestore.activities.WriteCommentActivity;
import com.example.onsalestore.activities.PostDetailActivity;
import com.example.onsalestore.objects.PostItem;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
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

        private TextView postItemUserName;
        private TextView numberOfLikes;
        private TextView numberOfComments;
        private ImageView postItemProfileImage;
        private ImageView postItemImage;
        private ImageView postItemComment;
        private ImageView postItemLike;
        private ImageView postItemShare;
        private CardView postItemCardView;
        private Boolean clicked = true;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postItemUserName = itemView.findViewById(R.id.postItemUserName);
            postItemProfileImage = itemView.findViewById(R.id.postItemProfileImage);
            postItemImage = itemView.findViewById(R.id.postItemImage);
            numberOfLikes = itemView.findViewById(R.id.postItemNumberOfLikes);
            numberOfComments = itemView.findViewById(R.id.postItemNumberOfComments);
            postItemLike = itemView.findViewById(R.id.postItemLike);
            postItemComment = itemView.findViewById(R.id.postItemComment);
            postItemShare = itemView.findViewById(R.id.postItemShare);
            postItemCardView = itemView.findViewById(R.id.postItemCardView);


            postItemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    PostItem postItem = postItemList.get(position);
                    Intent intent = new Intent(itemView.getContext(), PostDetailActivity.class);
                    intent.putExtra("EXTRA_ITEM", Parcels.wrap(postItem));
                    context.startActivity(intent);
                }
            });

            postItemComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    PostItem postItem = postItemList.get(position);
                    Intent intent = new Intent(itemView.getContext(), WriteCommentActivity.class);
                    intent.putExtra("EXTRA_ITEM", Parcels.wrap(postItem));
                    context.startActivity(intent);
                }
            });

            postItemLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clicked) {
                        clicked = false;
                        postItemLike.setImageResource(R.drawable.ic_vector_heart);
                        int position = getBindingAdapterPosition();
                        PostItem postItem = postItemList.get(position);
                        Integer numberOfLikes = postItem.getNumberOfLikes();
                        numberOfLikes = numberOfLikes + 1;
                        postItem.put("likes", numberOfLikes);
                        postItem.add("likeUserNames", ParseUser.getCurrentUser().getUsername());
                        postItem.saveInBackground();

                    } else {
                        clicked = true;
                        postItemLike.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        int position = getBindingAdapterPosition();
                        PostItem postItem = postItemList.get(position);
                        Integer numberOfLikes = postItem.getNumberOfLikes();
                        numberOfLikes = numberOfLikes - 1;
                        postItem.put("likes", numberOfLikes);
                        JSONArray jsonArray = postItem.getJSONArray("likeUserNames");
                        String currentUserName = ParseUser.getCurrentUser().getUsername();
                        if (jsonArray == null) {
                            return;
                        }
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            try {
                                if (currentUserName.equals(jsonArray.get(i))) {
                                    jsonArray.remove(i);
                                    postItem.put("likeUserNames", jsonArray);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        postItem.saveInBackground();
                    }
                }
            });

            postItemShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    PostItem postItem = postItemList.get(position);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "mycloset://post" + "/" + postItem.getObjectId());
                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    context.startActivity(shareIntent);
                }
            });
        }


        public void bind(PostItem item) {
            JSONArray jsonArrayComment = item.getJSONArray("comments");

            if (jsonArrayComment == null) {
                numberOfComments.setText("0");
            } else {
                numberOfComments.setText(Integer.toString(jsonArrayComment.length()));
            }
            postItemUserName.setText(item.getUser().getUsername());

            String image = item.getItemImageUrl();

            if (image != null) {
                Glide.with(context).load(image).into(postItemImage);
            }

            JSONArray jsonArrayLikeUsers = item.getJSONArray("likeUserNames");

            numberOfLikes.setText(Integer.toString(jsonArrayLikeUsers.length()));

            String currentUserName = ParseUser.getCurrentUser().getUsername();
            if (jsonArrayLikeUsers == null) {
                return;
            }
            for (int i = 0; i < jsonArrayLikeUsers.length(); ++i) {
                try {
                    if (currentUserName.equals(jsonArrayLikeUsers.get(i))) {
                        postItemLike.setImageResource(R.drawable.ic_vector_heart);
                    } else {
                        postItemLike.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
