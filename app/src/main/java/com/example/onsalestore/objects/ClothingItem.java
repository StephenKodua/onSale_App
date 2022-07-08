package com.example.onsalestore.objects;

import org.parceler.Parcel;

@Parcel
public class ClothingItem {

    private String itemName;
    private String itemImageUrl;
    private String itemPrice;
    private String itemSource;
    private String itemUrl;

    public ClothingItem(){

    }

    public ClothingItem(String itemName, String itemImageUrl, String itemPrice, String itemSource, String
                        itemUrl){
        this.itemName = itemName;
        this.itemImageUrl = itemImageUrl;
        this.itemPrice = itemPrice;
        this.itemSource = itemSource;
        this.itemUrl = itemUrl;
    }

    public String getItemName(){return itemName;}

    public String getItemImageUrl(){return itemImageUrl;}

    public String getItemPrice(){return itemPrice;}

    public String getItemSource(){return itemSource; }

    public String getItemUrl(){return itemUrl; }
}
