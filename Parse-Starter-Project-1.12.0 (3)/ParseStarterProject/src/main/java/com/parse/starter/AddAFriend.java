package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by Jaimie on 1/23/2016.
 */


public class AddAFriend extends AppCompatActivity {
    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_a_friend);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        Intent intentExtras = getIntent();
        if (intentExtras != null) {
            groupName = intentExtras.getStringExtra("group_name");
        }
    }
}


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

