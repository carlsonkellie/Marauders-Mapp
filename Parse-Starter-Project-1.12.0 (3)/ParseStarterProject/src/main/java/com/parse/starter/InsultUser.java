package com.parse.starter;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
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

import android.app.Activity;
import android.speech.RecognizerIntent;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by kelliecarlson on 1/23/16.
 */
public class InsultUser extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.insult_screen);

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

        public void functionone (View v, View w) {
            Intent insultIntent = new Intent(InsultUser.this, ISolemnlySwear.class);
            startActivity(insultIntent);
        }


}