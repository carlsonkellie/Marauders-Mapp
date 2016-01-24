package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.AddorDeleteFriends;
import com.parse.starter.R;
import com.parse.starter.Signup;

import java.util.ArrayList;
import java.util.List;

public class LocateFriend extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ParseUser p = ParseUser.getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locate_friends);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        List<ParseUser> arrayList1 = (ArrayList<ParseUser>) p.get("friends");

        if (arrayList1 == null) {
            arrayList1 = new ArrayList<ParseUser>();
        }

        final List<ParseUser> arrayList = arrayList1;
        final List<String> list = new ArrayList<String>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> t, ParseException e) {
                for (ParseUser u : t) {
                    if (arrayList.contains(u)) {
                        list.add(u.getUsername());
                    }
                }
            }
        });


        final ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                System.out.println("Item: "  + item);
                //query finds user with same username as the one you clicked on
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("username", item);
                query.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null) {
                            for (ParseUser u : objects) {
                                double lat = (double) u.get("latitude");
                                double lng = (double) u.get("longitude");
                                Intent intent = new Intent(LocateFriend.this, Map.class);
                                intent.putExtra("lat", lat);
                                intent.putExtra("long", lng);
                                startActivity(intent);
                            }
                        } else {
                            System.out.println("help");
                        }
                    }
                });
            }
        });
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


}