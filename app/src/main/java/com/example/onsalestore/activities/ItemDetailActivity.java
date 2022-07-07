package com.example.onsalestore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.onsale.R;
import com.example.onsalestore.adapters.ClothingItemAdapter;
import com.example.onsalestore.objects.ClosetItem;
import com.example.onsalestore.objects.ClothingItem;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.List;

public class ItemDetailActivity extends AppCompatActivity {

    TextView itemDetailName, itemDetailPrice, itemDetailSource;
    ImageView itemDetailImage;
    Button btnAddToCloset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        itemDetailImage = findViewById(R.id.itemDetailImage);
        itemDetailName = findViewById(R.id.itemDetailName);
        itemDetailSource = findViewById(R.id.itemDetailSource);
        itemDetailPrice = findViewById(R.id.itemDetailPrice);
        btnAddToCloset = findViewById(R.id.btnAddToCloset);

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

        btnAddToCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                ClosetItem closetItem = new ClosetItem(itemDetail);
                closetItem.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Toast.makeText(ItemDetailActivity.this, "Successfully Added To Closet", Toast.LENGTH_SHORT).show();
                        }
                        currentUser.add("closet", closetItem);
                        currentUser.saveInBackground();
                        Toast.makeText(ItemDetailActivity.this, "Successfully Added To Closet", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                });
            }
        });
    }
}