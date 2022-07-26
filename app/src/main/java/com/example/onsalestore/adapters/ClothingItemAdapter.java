package com.example.onsalestore.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.onsalestore.activities.ItemDetailActivity;
import com.example.onsalestore.objects.ClothingItem;

import org.parceler.Parcels;

import java.util.List;

public class ClothingItemAdapter extends RecyclerView.Adapter<ClothingItemAdapter.ViewHolder> {
    private Context context;
    private List<ClothingItem> itemsList;

    public ClothingItemAdapter(Context context, List<ClothingItem> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.clothing_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClothingItem item = itemsList.get(position);
        holder.bind(item);
        holder.homeItemCardView.startAnimation(AnimationUtils.loadAnimation(holder.homeItemCardView.getContext(),
                R.anim.anim_sliding));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvClothingName;
        private TextView tvClothingPrice;
        private ImageView ivClothingImage;
        private CardView homeItemCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClothingName = itemView.findViewById(R.id.tvClothingName);
            tvClothingPrice = itemView.findViewById(R.id.tvClothingPrice);
            ivClothingImage = itemView.findViewById(R.id.ivClothingImage);
            homeItemCardView = itemView.findViewById(R.id.homeItemCardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ClothingItem item = itemsList.get(position);
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra("EXTRA_ITEM", Parcels.wrap(item));
                    context.startActivity(intent);
                }
            });
        }

        public void bind(ClothingItem item) {
            tvClothingName.setText(item.getItemName());
            tvClothingPrice.setText(item.getItemPrice());
            String image = item.getItemImageUrl();
            if (image != null) {
                Glide.with(context).load(image).into(ivClothingImage);
            }
        }
    }
}
