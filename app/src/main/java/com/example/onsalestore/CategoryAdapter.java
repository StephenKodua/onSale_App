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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {
    public final String TAG = "CategoryAdapter";
    private Context context;
    private List<Category>categories;

    public CategoryAdapter (Context context, ArrayList<Category> categories){
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    //inflate each individual layout
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called.");
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new Viewholder(view);
    }

    //bind data to each individual item
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category);

    }

    @Override
    public int getItemCount() { return categories.size();}

    class Viewholder extends RecyclerView.ViewHolder {

        private TextView rvName;
        private ImageView rvImage;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            rvName = itemView.findViewById(R.id.rvName);
            rvImage = itemView.findViewById(R.id.rvImage);
        }

        public void bind(Category category) {

            rvName.setText(category.getName());
            ParseFile image = category.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(rvImage);
            }
        }
    }
}
