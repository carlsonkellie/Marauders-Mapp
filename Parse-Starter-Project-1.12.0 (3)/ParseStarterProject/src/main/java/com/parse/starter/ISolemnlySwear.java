/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseAnalytics;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class ISolemnlySwear extends Activity implements OnClickListener {

  protected static final int REQUEST_OK = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.i_solemnly_swear);
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
    findViewById(R.id.imageButton).setOnClickListener(this);

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

  @Override
  public void onClick(View v) {
    Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
    try {
      startActivityForResult(i, REQUEST_OK);
    } catch (Exception e) {
      Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
      ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
      if (thingsYouSaid.get(0).equals("I solemnly swear that I am up to no good")) {
        Intent mapIntroIntent = new Intent(ISolemnlySwear.this, MapIntro.class);
        startActivity(mapIntroIntent);
      } else {
        Intent insultIntent = new Intent(ISolemnlySwear.this, InsultUser.class);
        startActivity(insultIntent);
      }
    }
  }

}


