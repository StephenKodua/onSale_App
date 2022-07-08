package com.example.onsalestore.adapters;

import android.content.Context;
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
import com.example.onsalestore.objects.ClosetItem;

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
        private TextView closetItemName;
        private ImageView closetItemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            closetItemName = itemView.findViewById(R.id.closetItemName);
            closetItemImage = itemView.findViewById(R.id.closetItemImage);
        }

        public void bind(ClosetItem item) {
            Log.d("ClosetItemAdapter", item.getItemName());
            closetItemName.setText(item.getItemName());
            String image = item.getItemImageUrl();
            if (image != null) {
                Glide.with(context).load(image).into(closetItemImage);
            }
        }
    }
}
