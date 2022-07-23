package com.example.onsalestore.objects;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

@ParseClassName("PostItem")
public class PostItem extends ParseObject {

    public static final String KEY_ITEM_POST_IMAGE = "postImageUrl";
    public static final String KEY_LIKE = "likes";
    public static final String KEY_USER = "user";
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_OBJECT_ID = "objectId";

    public PostItem() {
    }

    public PostItem(ClosetItem closetItem) {
        setItemImageUrl(closetItem.getItemImageUrl());
        setUser();
    }

    public String getItemImageUrl() {
        return getString(KEY_ITEM_POST_IMAGE);
    }

    public void setItemImageUrl(String postImageUrl) {
        put(KEY_ITEM_POST_IMAGE, postImageUrl);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        put(KEY_USER, currentUser);
    }

    public Integer getNumberOfLikes() {
        return getInt(KEY_LIKE);
    }

}