package com.example.onsalestore.objects;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

@ParseClassName("CommentItem")
public class CommentItem extends ParseObject {
    public static final String KEY_USER = "user";
    public static final String KEY_COMMENT = "comment";

    public CommentItem() {
    }

    public CommentItem(String message) {
        setComment(message);
        setUser();
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        put(KEY_USER, currentUser);
    }

    public void setComment(String comment) {
        put(KEY_COMMENT, comment);
    }

}
