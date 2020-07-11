package com.ukteams.ecommerce.Sellers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ukteams.ecommerce.R;

import java.util.HashMap;

public class SellerRegistrationActivity extends AppCompatActivity {

    private Button sellerLoginBegin;
    private EditText nameInput, phoneInput, emailInput, passwordInput, addressInput;
    private Button registerButton;
    private ProgressDialog loadingBar;


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_regisration);


        mAuth = FirebaseAuth.getInstance();   //initializing Firebase auth (Login authentication)
        loadingBar = new ProgressDialog(this); //setting the progress dialog bar

        //initialize
        nameInput = findViewById(R.id.seller_name);
        phoneInput = findViewById(R.id.seller_phone);
        emailInput = findViewById(R.id.seller_email);
        passwordInput = findViewById(R.id.seller_password);
        addressInput = findViewById(R.id.seller_address);
        registerButton = findViewById(R.id.seller_register_btn);
        sellerLoginBegin = (Button) findViewById(R.id.seller_already_have_account_btn);


        //set onclick listener for the Seller login begin
        sellerLoginBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                startActivity(intent);
            }
        });


        //register button onclick listiner
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerSeller();

            }
        });

    }



    //register seller method
    private void registerSeller() {

        final String name = nameInput.getText().toString();
        final String phone = phoneInput.getText().toString();
        final String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        final String address = addressInput.getText().toString();

        //then lets verify these parameters to make sure that the user doesnt submit a blank form

        if(!name.equals("") && !phone.equals("") && !email.equals("") && !password.equals("") && !address.equals("")){

            //progress dialog bar
            loadingBar.setTitle("Creating Seller Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

             mAuth.createUserWithEmailAndPassword(email,password)
                     .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {

                             if(task.isSuccessful()){

                                 //save the user's info into the firebase database
                                 final DatabaseReference rootRef;
                                 rootRef = FirebaseDatabase.getInstance().getReference();

                                 String sid = mAuth.getCurrentUser().getUid();

                                 HashMap<String, Object> sellerMap = new HashMap<>();
                                 sellerMap.put("sid",sid);
                                 sellerMap.put("phone",phone);
                                 sellerMap.put("email",email);
                                 sellerMap.put("address",address);
                                 sellerMap.put("name",name);

                                 rootRef.child("Sellers").child("sid").updateChildren(sellerMap)
                                         .addOnCompleteListener(new OnCompleteListener<Void>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Void> task) {

                                                 loadingBar.dismiss();
                                                 Toast.makeText(SellerRegistrationActivity.this,"You have been successfully Registered",Toast.LENGTH_SHORT).show();

                                                 Intent intent = new Intent(SellerRegistrationActivity.this, SellerHomeActivity.class);
                                                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                 startActivity(intent);
                                                 finish();

                                             }
                                         });





                             }

                         }
                     });

        }else {

            Toast.makeText(SellerRegistrationActivity.this,"Please complete the Registration form",Toast.LENGTH_LONG).show();

        }




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