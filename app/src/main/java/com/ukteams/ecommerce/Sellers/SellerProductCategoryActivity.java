package com.ukteams.ecommerce.Sellers;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ukteams.ecommerce.R;

public class SellerProductCategoryActivity extends AppCompatActivity {

    private ImageView sweater, tShirts, golf_tshirt, jackets;
    private ImageView track_bottom, short_skin_tight, long_skin_tights, vests;
    private ImageView socks, shorts, watches, mobilePhones;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product_category);


        sweater = (ImageView) findViewById(R.id.sweater);
        tShirts = (ImageView) findViewById(R.id.t_shirts);
        golf_tshirt = (ImageView) findViewById(R.id.golf_tshirt);
        jackets = (ImageView) findViewById(R.id.jackets);

        track_bottom = (ImageView) findViewById(R.id.track_bottom);
        short_skin_tight = (ImageView) findViewById(R.id.short_skin_tight);
        long_skin_tights = (ImageView) findViewById(R.id.long_skin_tight);
        vests = (ImageView) findViewById(R.id.vests);

        socks = (ImageView) findViewById(R.id.socks);
        shorts = (ImageView) findViewById(R.id.shorts);
       // watches = (ImageView) findViewById(R.id.watches);
      //  mobilePhones = (ImageView) findViewById(R.id.mobilephones);




       //onclick listener on sweater to open the SellerAddNewProductActivity
        sweater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Sweaters"); //these tshirts have a category called "sweaters". This category will be stored and referenced in firebase database
                startActivity(intent);
            }
        });

        //onclick listener on Tshirts to open the SellerAddNewProductActivity
        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Tshirts"); //these tshirts have a category called "Tshirts". This category will be stored and referenced in firebase database
                startActivity(intent);
            }
        });


        //onclick listener on golf tshirt to open the SellerAddNewProductActivity
        golf_tshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Golf Tshirts"); //these Female Dresses have a category called "Tshirts ". This category will be stored and referenced in firebase database
                startActivity(intent);
            }
        });


        //onclick listener on Sweathers to open the SellerAddNewProductActivity
        jackets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Jackets"); //these Sweathers have a category called "Jackets ". This category will be stored and referenced in firebase database
                startActivity(intent);
            }
        });


        //onclick listener on track bottom to open the SellerAddNewProductActivity
        track_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Track Bottom"); //these glasses have a category called "Track Bottom ". This category will be stored and referenced in firebase database
                startActivity(intent);
            }
        });


        //onclick listener on short skin tight to open the SellerAddNewProductActivity
        short_skin_tight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Short Skin Tights"); //these hatsCaps have a category called "Short Skin Tights ". This category will be stored and referenced in firebase database
                startActivity(intent);
            }
        });


        //onclick listener on long skin tighs to open the SellerAddNewProductActivity
        long_skin_tights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Long Skin Tights"); //these walletsBagsPurses have a category called "Long Skin Tights ". This category will be stored and referenced in firebase database
                startActivity(intent);
            }
        });


        //onclick listener on vests to open the SellerAddNewProductActivity
        vests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Vests"); //these shoes have a category called "Vests ". This category will be stored and referenced in firebase database
                startActivity(intent);
            }
        });


        //onclick listener on socks to open the SellerAddNewProductActivity
        socks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Socks"); //these headPhonesHandFree have a category called "Socks ". This category will be stored and referenced in firebase database
                startActivity(intent);
            }
        });



        //onclick listener on shorts to open the SellerAddNewProductActivity
        shorts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Shorts"); //these Laptops have a category called "Shorts ". This category will be stored and referenced in firebase database
                startActivity(intent);
            }
        });

/*
        //onclick listener on watches to open the SellerAddNewProductActivity
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Watches"); //these watches have a category called "Watches ". This category will be stored and referenced in firebase database
                startActivity(intent);
            }
        });


        //onclick listener on mobilePhones to open the SellerAddNewProductActivity
        mobilePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Mobile Phones"); //these mobilePhones have a category called "Mobile Phones ". This category will be stored and referenced in firebase database
                startActivity(intent);
            }
        });*/


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