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
import com.ukteams.ecommerce.R;

public class SellerLoginActivity extends AppCompatActivity {

    private Button registerSellerBtn;
    private EditText emailInput, passwordInput;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);


        mAuth = FirebaseAuth.getInstance();   //initializing Firebase auth (Login authentication)
        loadingBar = new ProgressDialog(this); //setting the progress dialog bar


        //initializing
        emailInput = findViewById(R.id.seller_login_email);
        passwordInput = findViewById(R.id.seller_login_password);
        registerSellerBtn = findViewById(R.id.seller_login_btn);


        //onclicklistiner on register seller btn
        registerSellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginSeller();

            }


        });


    }


//loginSeller method
    private void loginSeller() {

        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();


        //then lets verify these parameters to make sure that the user doesnt submit a blank form
        if (!email.equals("") && !password.equals("")) {

            //progress dialog bar
            loadingBar.setTitle("Seller Account Login");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                              if(task.isSuccessful()){

                                  Intent intent = new Intent(SellerLoginActivity.this, SellerHomeActivity.class);
                                  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                  startActivity(intent);
                                  finish();

                              }

                        }
                    });

        }else{

            Toast.makeText(SellerLoginActivity.this,"Please complete the Login form",Toast.LENGTH_LONG).show();

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