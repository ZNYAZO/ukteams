package com.ukteams.ecommerce.Admin;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.ukteams.ecommerce.Model.Products;
import com.ukteams.ecommerce.R;
import com.ukteams.ecommerce.ViewHolder.ProductViewHolder;

public class AdminCheckNewProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unverifiedProductsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_new_products);


        unverifiedProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        //initialize
        recyclerView = findViewById(R.id.admin_products_checklist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //add firebase query so as to retrieve all the unverified products
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(unverifiedProductsRef.orderByChild("productState")
                .equalTo("Not Approved"), Products.class)
                .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {

                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                       //holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                        holder.txtProductPrice.setText("Price = " + "R" + model.getPrice());
                        Picasso.get().load(model.getImage()).into(holder.imageView);


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                final String productID = model.getPid();

                                CharSequence options[] = new CharSequence[]{

                                        "Yes",
                                        "No"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminCheckNewProductsActivity.this);
                                builder.setTitle("Do you want to Approve this Product. Are you Sure?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int position) {

                                        if(position == 0){ //Yes: product approved for sell

                                            changeProductState(productID);
                                        }

                                        if(position == 1){



                                        }

                                    }
                                });

                                builder.show();//so that it can show the dialogue box

                            }
                        });


                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;

                    }
                };


        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }


//changeProductState method
    private void changeProductState(String productID) {

        unverifiedProductsRef.child(productID)
                .child("productState")
                .setValue("Approved")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(AdminCheckNewProductsActivity.this,"This item has been approved and it is now available for Sell from the Seller",Toast.LENGTH_SHORT).show();

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