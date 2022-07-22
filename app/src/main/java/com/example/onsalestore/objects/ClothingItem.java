package com.example.onsalestore.objects;

import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

@Parcel
public class ClothingItem {

    private String itemName;
    private String itemImageUrl;
    private String itemPrice;
    private String itemSource;
    private String itemUrl;
    private ParseUser user;

    public ClothingItem(){
    }

    public ClothingItem(String itemName, String itemImageUrl, String itemPrice, String itemSource,
                        String itemUrl, ParseUser user) {

        this.itemName = itemName;
        this.itemImageUrl = itemImageUrl;
        this.itemPrice = itemPrice;
        this.itemSource = itemSource;
        this.itemUrl = itemUrl;
        this.user = user;
    }

    public String getItemName(){return itemName;}

    public ParseUser getUser(){return user;}

    public String getItemImageUrl(){return itemImageUrl;}

    public String getItemPrice(){return itemPrice;}

    public String getItemSource(){return itemSource; }

    public String getItemUrl(){return itemUrl; }


}
