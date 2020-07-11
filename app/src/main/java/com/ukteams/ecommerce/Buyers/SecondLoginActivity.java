package com.ukteams.ecommerce.Buyers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ukteams.ecommerce.R;

public class SecondLoginActivity extends AppCompatActivity {

    Button login;  // ENTER PASSWORD BUTTON from activity_first_login.xml
    SharedPreferences sp;  //SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_login);

        //find View which has the Submit button which is in activity_first_login.xml
        login = (Button) findViewById(R.id.submitbutton);


        // initializing in our app with name “login” and mode “private”.
        sp = getSharedPreferences("login", MODE_PRIVATE);


        // Find the View that shows shop entrance text "Click here to go to the Shop" which is activity_second_login.xml
        TextView shopEntrance = (TextView) findViewById(R.id.shop_entrance);

        // Set a click listener on that View
        shopEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                // Create a new explicit intent to open the MainActivity
                Intent loginIntent = new Intent(SecondLoginActivity.this, FirstLoginActivity.class);

                // Start the new activity
                startActivity(loginIntent);


                //and every time the activity starts, check for the value.
                // If we get true, it means login is completed, because only after login,
                // we are making it true. If we get false, it means login is not done,
                // because the default value of Boolean is false.
                // So if we know that user has logged in already it will skip the Login on FirstLoginActivity (Which has our login platform)
                // and go straight to MainActivity which is our main content landing page
                if (sp.getBoolean("logged",false)) {
                    goToMainActivity();

                }

            }
        });




        // Find the View that shows the logo
        ImageView logo = (ImageView) findViewById(R.id.logo);

        // Set a click listener on that View
        logo.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the logo is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new explicit intent to open the {@link FirstLoginActivity} (Which has our login platform)
                Intent logoIntent = new Intent(SecondLoginActivity.this, FirstLoginActivity.class);

                // Start the new activity
                startActivity(logoIntent);


                //Checks if its in Logged State, if so, should proceed to MainActivity which is our main content landing page
                // else give wrong password, try again error message through the Toast and dont proceed
                //until the password is right, then proceed to MainActivity

                if (sp.getBoolean("logged",false)) {
                    goToMainActivity();

                }


            }
        });



    }



    // We use the goToMainActivity() method, to move to MainActivity.
    public void goToMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
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