package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.FindCallback;
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
public class MaintainFriends extends AppCompatActivity {
    String groupName = "Friends";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintain_friends);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        Intent intentExtras = getIntent();
        if (intentExtras != null) {
            groupName = intentExtras.getStringExtra("group_name");
        }

        TextView groupname = (TextView) findViewById(R.id.locatefriend);
        groupname.setText(groupName);

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


    public void addFriend (View view) {
        Intent intent = new Intent(this, AddAFriend.class);
        intent.getStringExtra(groupName);
        startActivity(intent);
    }

    public void leaveGroup(View view) {

        final ParseUser p = ParseUser.getCurrentUser();
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Group"); //from group class
            query.whereEqualTo("name", groupName); //particular group you want to delete self from
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> t, ParseException e) {
                    if (e == null) {
                        final ParseObject po = t.get(0);
                        ArrayList<ParseUser> users = new ArrayList<ParseUser>();
                        if (po.get("friends") == null) {
                            po.put("friends", users);
                        }
                        users = (ArrayList<ParseUser>) po.get("friends");
                        users.remove(ParseUser.getCurrentUser().getObjectId()); //delete self from group
                        po.put("friends", users);
                        po.saveInBackground();
                        //now delete group from self


                        if (p.get("Groups") == null) {
                            System.out.println("is this always happening?");
                            ArrayList<ParseObject> myList = new ArrayList<ParseObject>();
                            p.put("Groups", myList);
                        }
                        final ArrayList<ParseObject> myList = (ArrayList<ParseObject>) p.get("Groups");
                        myList.remove(po);
                       // for (ParseObject o : myList) {
                        //    System.out.println(o.getObjectId());
                         //   ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
                          //  query.getInBackground(po.getObjectId(), new GetCallback<ParseObject>() {
                           //     @Override
                            //    public void done(ParseObject object, ParseException e) {
                             //       if (e == null) {
                              //          if (object.get("name").equals(po.get("name"))) {
                               //             myList.remove(object);
                                //        }
                                 //   } else {
                                        //do nothing
                                 //   }
                               // }
                           // });
                        //}
                        //remove current user
                    } else {
                        System.out.println("exception");
                    }
                }
            });

            Intent intent = new Intent(this, MaintainGroups.class);
            startActivity(intent);



    }

}
