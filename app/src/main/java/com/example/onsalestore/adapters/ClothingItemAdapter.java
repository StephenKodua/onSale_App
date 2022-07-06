package com.example.onsalestore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onsale.R;
import com.example.onsalestore.objects.ClothingItem;

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
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName, itemPrice;
        private ImageView itemImage, itemDetailImage;
        private TextView itemDetailName, itemDetailPrice, itemDetailSource;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemImage = itemView.findViewById(R.id.itemImage);


        }

        public void bind(ClothingItem item) {
            itemName.setText(item.getItemName());
            itemPrice.setText(item.getItemPrice());

            String image = item.getItemImageUrl();
            if (image != null) {
                Glide.with(context).load(image).into(itemImage);
            }
        }
    }
}
