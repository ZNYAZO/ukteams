package com.ukteams.ecommerce.Admin;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ukteams.ecommerce.Buyers.HomeActivity;
import com.ukteams.ecommerce.Buyers.MainActivity;
import com.ukteams.ecommerce.R;

public class AdminHomeActivity extends AppCompatActivity {

 private Button LogoutBtn, CheckOrdersBtn, maintainProductsBtn,checkApproveProductsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);



        //initializing the views
        LogoutBtn = (Button)findViewById(R.id.admin_logout_btn);
        CheckOrdersBtn = (Button)findViewById(R.id.check_orders_btn);
        checkApproveProductsBtn = findViewById(R.id.check_approve_products_btn);
        maintainProductsBtn = (Button)findViewById(R.id.maintain_btn);

        //maintainProductsBtn OnClickListener
        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminHomeActivity.this, HomeActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);


            }
        });

        //logout OnClickListener
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        //Checkorders OnClickListener
        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminHomeActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);


            }
        });


        //checkApproveProductsBtn OnClickListener
        checkApproveProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminHomeActivity.this, AdminCheckNewProductsActivity.class);
                startActivity(intent);


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