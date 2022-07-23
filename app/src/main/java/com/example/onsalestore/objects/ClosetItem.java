package com.example.onsalestore.objects;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.Serializable;

@ParseClassName("ClosetItem")
public class ClosetItem extends ParseObject implements Serializable {
    public static final String KEY_ITEM_NAME = "itemName";
    public static final String KEY_ITEM_IMAGE_URL = "itemImageUrl";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";

    public ClosetItem() {
    }

    public ClosetItem(ClothingItem clothingItem) {

        setItemName(clothingItem.getItemName());
        setItemImageUrl(clothingItem.getItemImageUrl());
        setUser();

    }

    public String getItemName() {
        return getString(KEY_ITEM_NAME);
    }

    public void setItemName(String name) {
        put(KEY_ITEM_NAME, name);
    }

    public String getItemImageUrl() {
        return getString(KEY_ITEM_IMAGE_URL);
    }

    public void setItemImageUrl(String imageUrl) {
        put(KEY_ITEM_IMAGE_URL, imageUrl);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        put(KEY_USER, currentUser);
    }

    //Model Class
    private boolean isChecked = true;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
