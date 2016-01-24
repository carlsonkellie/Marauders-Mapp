package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaimie on 1/23/2016.
 */
public class DeleteFriend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final ParseUser p = ParseUser.getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_friends);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        //puts all the users in the list

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

       // ParseQuery<ParseUser> query = ParseUser.getQuery();
       // query.findInBackground(new FindCallback<ParseUser>() {
        //    public void done(List<ParseUser> t, ParseException e) {
         //       for (ParseUser u : t) {
          ///          arrayList.add(u);
            //        list.add(u.getUsername());
             //   }
           // }
       // });

        final ListView listView = (ListView) findViewById(R.id.listView2);
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
                                //removes that user from your friends
                                if (arrayList.contains(u)) {
                                    arrayList.remove(u); //remove objects
                                    p.remove("friends"); //delete row
                                    p.put("friends", arrayList); //add it back
                                }
                            }
                            p.saveInBackground();
                            Intent intent = new Intent(DeleteFriend.this, Map.class);
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
