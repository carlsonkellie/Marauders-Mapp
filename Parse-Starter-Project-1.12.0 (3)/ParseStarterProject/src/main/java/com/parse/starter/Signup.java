/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class Signup extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.signup);

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

  public void signup(View view) {
    TextView username = (TextView) findViewById(R.id.username2);
    TextView password = (TextView) findViewById(R.id.password2);
    TextView email = (TextView) findViewById(R.id.email3);

    ParseUser user = new ParseUser();
    user.setUsername(username.getText().toString());
    user.setPassword(password.getText().toString());
    user.setEmail(email.getText().toString());
    System.out.println(email.getText().toString());
    System.out.println(username.getText().toString());

    user.signUpInBackground(new SignUpCallback() {
      public void done (ParseException e) {
        if (e == null) {
          //no error
          System.out.println("success");
          Intent intent = new Intent(Signup.this, MainActivity.class);
          startActivity(intent);
        } else {
          //error
          System.out.println("failure " + e.getMessage());
        }
      }
    });
  }
}
