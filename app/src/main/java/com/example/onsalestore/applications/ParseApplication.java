package com.example.onsalestore.applications;

import android.app.Application;

import com.example.onsalestore.objects.ClosetItem;
import com.example.onsalestore.objects.CommentItem;
import com.example.onsalestore.objects.PostItem;
import com.parse.Parse;
import com.parse.ParseObject;

import org.w3c.dom.Comment;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Register your parse models
        ParseObject.registerSubclass(ClosetItem.class);
        ParseObject.registerSubclass(CommentItem.class);
        ParseObject.registerSubclass(PostItem.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("w83AQqV7J9BeKvLmmr0n1B64wVK0QhxVQWHowWFn")
                .clientKey("kxMxfBzSj5tYV4SJR3Ol6ImC44iZtgrCnGEJmgCQ")
                .server("https://parseapi.back4app.com").enableLocalDataStore()
                .build()
        );
    }
}
