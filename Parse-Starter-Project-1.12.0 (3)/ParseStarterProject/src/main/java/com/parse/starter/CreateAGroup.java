package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by Jaimie on 1/23/2016.
 */
public class CreateAGroup extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_a_group);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createGroup(View view) {
        //create a group
        TextView groupName = (TextView) findViewById(R.id.editText4);
        String name = groupName.getText().toString();
        ParseObject p = new ParseObject("Group");
        p.put("name", name);
        //add user to group
        ParseUser user = ParseUser.getCurrentUser();
        p.add("friends", user);
        p.saveInBackground();

        //add group to user
        user.add("Groups", p);
        user.saveInBackground();

        Intent intent = new Intent(CreateAGroup.this, MaintainGroups.class);
        startActivity(intent);

    }

}
