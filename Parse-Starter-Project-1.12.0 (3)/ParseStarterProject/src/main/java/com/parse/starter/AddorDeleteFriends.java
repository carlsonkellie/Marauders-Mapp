package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.parse.ParseAnalytics;
import com.parse.starter.R;

/**
 * Created by Jaimie on 1/23/2016.
 */
public class AddorDeleteFriends extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_or_delete_friends);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }

    public void addFriends(View view) {
        Intent intent = new Intent(this, AddAFriend.class);
        startActivity(intent);

    }

    public void deleteFriends(View view) {
        Intent  intent = new Intent(this, DeleteFriend.class);
        startActivity(intent);

    }

}