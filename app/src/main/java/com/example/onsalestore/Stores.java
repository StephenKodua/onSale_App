package com.example.onsalestore;


import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Stores")
public class Stores extends ParseObject {
    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";


    public String getName(){
        return getString(KEY_NAME);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

}
