package com.example.onsalestore;

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
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.Viewholder> {
    public final String TAG = "StoresAdapter";
    private Context context;
    private List<Stores> stores;

    public StoresAdapter(Context context, ArrayList<Stores> stores) {
        this.context = context;
        this.stores = stores;
    }

    @NonNull
    @Override
    //inflate each individual layout
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_item, parent, false);
        return new Viewholder(view);
    }

    //bind data to each individual item
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Stores store = stores.get(position);
        holder.bind(store);
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        private TextView rvStoreName;
        private ImageView rvStoreImage;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            rvStoreName = itemView.findViewById(R.id.rvStoreName);
            rvStoreImage = itemView.findViewById(R.id.rvStoreImage);
        }

        public void bind(Stores store) {
            rvStoreName.setText(store.getName());
            ParseFile image = store.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(rvStoreImage);
            }
        }
    }
}
