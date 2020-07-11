package com.ukteams.ecommerce.Buyers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ukteams.ecommerce.R;

public class FirstLoginActivity extends AppCompatActivity {

    Button login; // ENTER PASSWORD BUTTON
    SharedPreferences sp; //SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);

        //find View which has submitbutton
        login = (Button) findViewById(R.id.submitbutton);

        // initializing in our app with name “login” and mode “private”.
        sp = getSharedPreferences("login", MODE_PRIVATE);

        //OnClickLister which checks password on the "passwordedittext" if its ok and move to
        // MainActivity which is our main content landing page , if not give a Toast error message,
        //  if its correct store the logged in state in the
        // sp.edit().putBoolean("logged",true).apply();
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText passwordEditText = (EditText) findViewById(R.id.passwordedittext);

                //Checks if password is equals "1x2y3z", is so, save the logged in State through
                // the sp.edit().putBoolean("logged",true).apply(); else give
                //wrong password, try again error message through the Toast
                if (passwordEditText.getText().toString().equals("1x2y3z")) {
                    //if the password is correct, go to MainActivity (which is our main content landing page)
                    Intent intent = new Intent(FirstLoginActivity.this, MainActivity.class);

                    // Start the new activity
                    startActivity(intent);

                    //now sp is ready to store values. To check if the user is logged in once into the app,
                    // let’s save a boolean value is Logged into sp after login is completed.
                    sp.edit().putBoolean("logged",true).apply();

                } else {

                    //wrong password, try again
                    Toast.makeText(FirstLoginActivity.this, "Wrong Password. Try again.", Toast.LENGTH_LONG).show();
                }


            }


        });
    }



    // this two line onConfigurationChanged method is responsible to maker user on change of orientation
    // the app does not restart, rather it continues from whatever its happening. we also added permission
    // to handle that on Manifest.xml on opening "activity tag"

    // <activity android:name=".Buyers.MainActivity"
    //            android:configChanges="keyboardHidden|orientation|screenSize|layoutDirection|uiMode">

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }


}