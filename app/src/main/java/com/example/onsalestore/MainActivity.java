package com.example.onsalestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.onsale.R;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //execute if menu item is tapped
        if (item.getItemId() == R.id.profileItem ){
            ParseUser.logOutInBackground(new LogOutCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        goToLoginActivity();
                    } else {
                        Toast.makeText(MainActivity.this, "Error signing out", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return true;
    }

    //Launch Login Activity
    private void goToLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}