package com.example.onsalestore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.onsale.R;
import com.example.onsalestore.adapters.ClothingItemAdapter;
import com.example.onsalestore.objects.ClothingItem;

import org.parceler.Parcels;

import java.util.List;

public class ItemDetailActivity extends AppCompatActivity {

    TextView itemDetailName, itemDetailPrice, itemDetailSource;
    ImageView itemDetailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        itemDetailImage = findViewById(R.id.itemDetailImage);
        itemDetailName = findViewById(R.id.itemDetailName);
        itemDetailSource = findViewById(R.id.itemDetailSource);
        itemDetailPrice = findViewById(R.id.itemDetailPrice);

        ClothingItem itemDetail = Parcels.unwrap(getIntent().getParcelableExtra("EXTRA_ITEM"));

        String itemImageUrl = itemDetail.getItemImageUrl();
        String itemName = itemDetail.getItemName();
        String itemSource = itemDetail.getItemSource();
        String itemPrice = itemDetail.getItemPrice();

        itemDetailName.setText(itemName);
        itemDetailPrice.setText(itemPrice);
        itemDetailSource.setText(itemSource);

        if (itemImageUrl != null) {
            Glide.with(this).load(itemImageUrl).into(itemDetailImage);
        }
    }
}