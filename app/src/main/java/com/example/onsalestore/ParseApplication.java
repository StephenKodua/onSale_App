package com.example.onsalestore;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("w83AQqV7J9BeKvLmmr0n1B64wVK0QhxVQWHowWFn")
                .clientKey("kxMxfBzSj5tYV4SJR3Ol6ImC44iZtgrCnGEJmgCQ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
