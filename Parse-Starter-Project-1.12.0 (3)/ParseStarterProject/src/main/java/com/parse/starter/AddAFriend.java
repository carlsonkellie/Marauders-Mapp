package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaimie on 1/23/2016.
 */


public class AddAFriend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final ParseUser p = ParseUser.getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_a_friend);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        //puts all the users in the list
        final ArrayList<ParseUser> arrayList = new ArrayList<ParseUser>();
        final ArrayList<String> list = new ArrayList<String>();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> t, ParseException e) {
                for (ParseUser u : t) {
                    arrayList.add(u);
                    list.add(u.getUsername());
                }
            }
        });

        ArrayList<ParseUser> friendsList = (ArrayList<ParseUser>) p.get("friends");
        if (friendsList == null) {
            friendsList = new ArrayList<ParseUser>();
        }
        final ArrayList<ParseUser> friendsList1 = friendsList;

        final ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                //query finds user with same username as the one you clicked on
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("username", item);
                query.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null) {
                        //adds that friend to username
                           for (ParseUser o: objects) {
                               System.out.println("We have a user!!!!!");
                                friendsList1.add(o);
                            }
                            System.out.println("We got here!");
                            p.put("friends", friendsList1);
                            p.saveInBackground();
                            Intent intent = new Intent(AddAFriend.this, Map.class);
                            startActivity(intent);
                        } else {
                            System.out.println("help");
                        }
                    }
                });
            }
        });
    }

}
/**
        //Get the group that I want to put the person into
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Group");
        query2.getInBackground(groupName, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    //object is the group which we want
                } else {
                    System.out.println("exception");
                }
            }
        });

*/



        //query to get the group with that group name
        //then add the person to it



/**

        final ListView listView = (ListView) findViewById(R.id.listView2);
      // ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                Intent intentExtras = new Intent(MaintainGroups.this, MaintainFriends.class);
                intentExtras.putExtra("group_name", item);
                startActivity(intentExtras);
            }
        });
    }
*/

