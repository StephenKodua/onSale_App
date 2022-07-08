package com.example.onsalestore.objects;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("ClosetItem")
public class ClosetItem extends ParseObject {
    public static final String KEY_ITEM_NAME = "itemName";
    public static final String KEY_ITEM_IMAGE_URL = "itemImageUrl";

    public ClosetItem(){}

    public ClosetItem(ClothingItem clothingItem){
        //Add item to closet
        setItemName(clothingItem.getItemName());
        setItemImageUrl(clothingItem.getItemImageUrl());

    }

    public String getItemName(){return getString(KEY_ITEM_NAME);}

    public String getItemImageUrl(){return getString(KEY_ITEM_IMAGE_URL);}

    public void setItemName(String name){put(KEY_ITEM_NAME,name);}

    public void setItemImageUrl(String imageUrl){put(KEY_ITEM_IMAGE_URL,imageUrl);}

}
