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

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaimie on 1/23/2016.
 */
public class MaintainGroups extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintain_groups);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

      final ParseUser p = ParseUser.getCurrentUser();
        if (p.get("Groups") == null) {
            System.out.println("is this always happening?");
            ArrayList<ParseObject> myList = new ArrayList<ParseObject>();
            p.put("Groups", myList);
        }

        ArrayList<ParseObject> myList = (ArrayList<ParseObject>) p.get("Groups");
        final ArrayList<String>list = new ArrayList<String>();

        for (ParseObject o: myList) {
            System.out.println(o.getObjectId());
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
            query.getInBackground(o.getObjectId(), new GetCallback<ParseObject>() {
                @Override
                public void done (ParseObject object, ParseException e) {
                    if (e == null) {
                        if (object.containsKey("name")) {
                            list.add((String)object.get("name"));
                            object.saveInBackground();

                        }
                    } else {
                        System.out.println("exception");
                    }
                }
            });


           // if (o.containsKey("name")) {
            //    String name = (String) o.get("name");
             //   list.add(name);
              //  o.saveInBackground();
               // p.saveInBackground();
           // } else {
            //    System.out.println("HALLLP");
           // }
        }


       final ListView listView = (ListView) findViewById(R.id.listView2);
       final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
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

    public void createGroup (View view) {
        Intent intent =  new Intent(this, CreateAGroup.class);
        startActivity(intent);
    }


}
