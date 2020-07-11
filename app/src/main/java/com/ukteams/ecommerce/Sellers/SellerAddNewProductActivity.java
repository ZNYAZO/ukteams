package com.ukteams.ecommerce.Sellers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ukteams.ecommerce.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SellerAddNewProductActivity extends AppCompatActivity {

    private String CategoryName, Description, Price, Pname, saveCurrentDate, saveCurrentTime;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private EditText InputProductName, InputProductDescription, InputProductPrice;
    private String sName, sAddress, sPhone, sEmail, sID;
    private static final int GalleryPick = 1; //gallery image picker from your device
    private Uri ImageUri;
    private String productRandomKey, downloadImageUrl;   //use saveCurrentDate and saveCurrentTime to create product random key when saving your products,
                                                         // since current time will never be the same for every product as it changes every second

    private StorageReference ProductImagesRef; //StorageReference: firebase database storage
                                               //filepath will be the URL of the image path stored in your firebase database storage

    private DatabaseReference ProductsRef, sellersRef;  //reference to the database on firebase
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_new_product);



        CategoryName = getIntent().getExtras().get("category").toString(); //gets all the categories, converts to string
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Products Images"); //we will create a "Products Images" folder in firebase where we will reference from
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products"); ////reference to the database on firebase, with the node called "Products" on firebase
        sellersRef = FirebaseDatabase.getInstance().getReference().child("Sellers"); ////reference to the database on firebase, with the node called "Sellers" on firebase

        //initializing
        AddNewProductButton = (Button) findViewById(R.id.add_new_product);
        InputProductImage = (ImageView) findViewById(R.id.select_product_image);
        InputProductName = (EditText) findViewById(R.id.product_name);
        InputProductDescription = (EditText) findViewById(R.id.product_description);
        InputProductPrice = (EditText) findViewById(R.id.product_price);
        loadingBar = new ProgressDialog(this);



       //onclick listiner on InputProductImage to open OpenGallery()method which opens the image gallery on your device
        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });



        //onclick listener on AddNewProductButton to validate product data using the method ValidateProductData()
        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateProductData();
            }
        });


          sellersRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                     .addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                             if(dataSnapshot.exists()){

                                  sName = dataSnapshot.child("name").getValue().toString();
                                  sAddress = dataSnapshot.child("address").getValue().toString();
                                  sPhone = dataSnapshot.child("phone").getValue().toString();
                                  sID = dataSnapshot.child("sid").getValue().toString();
                                  sEmail = dataSnapshot.child("email").getValue().toString();

                             }


                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });
    }

//OpenGallery method which opens the image gallery on your device to choose images
    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    //store the images selected on the firebase database storage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null) {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);
        }
    }

  //ValidateProductData method to make sure that the fields are not empty: InputProductDescription, InputProductPrice and InputProductName
    private void ValidateProductData() {
        Description = InputProductDescription.getText().toString();
        Price = InputProductPrice.getText().toString();
        Pname = InputProductName.getText().toString();


        if (ImageUri == null) {
            Toast.makeText(this, "Products image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description)) {
            Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price)) {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pname)) {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }

    //StoreProductInformation method to store the date and time of image being uploaded into firebase database storage
    private void StoreProductInformation() {
        loadingBar.setTitle("Add New Products");
        loadingBar.setMessage("Dear Seller, please wait while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        //use saveCurrentDate and saveCurrentTime to create product random key when saving your products,
        // since current time will never be the same for every product as it changes every second
        //product random key is to make sure that each and every product's data is unique so that it cannot be used by another product
        productRandomKey = saveCurrentDate + saveCurrentTime;

        //StorageReference: will be the URL of the image path stored in your firebase database storage
        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg"); //getLastPathSegment() gives you the image name

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        //add the FailureListener to handle image upload failures
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(SellerAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }

            //add the Success Listener to let the admin know that the images were uploaded successfully
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(SellerAddNewProductActivity.this, "Products Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                //when the product images have been successfully uploaded to firebase storage database, you need the image's URL to use it to display the image into the App
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception { //Otherwise if upload is not successful, throw an error message
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        //this one stores the image URL path
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }

                    //otherwise if the Products image upload is successful add this OnCompleteListener to let the admin know about the success upload
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(SellerAddNewProductActivity.this, "got the Products image Url Successfully...", Toast.LENGTH_SHORT).show();

                            //then call on SaveProductInfoToDatabase() method to save all the product information to firebase storage database
                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }



// this method saves all the product information to firebase storage database using hashMap library
    private void SaveProductInfoToDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("category", CategoryName);
        productMap.put("price", Price);
        productMap.put("pname", Pname);


        productMap.put("sellerName", sName);
        productMap.put("sellerAddress", sAddress);
        productMap.put("sellerPhone", sPhone);
        productMap.put("sellerEmail", sEmail);
        productMap.put("sid", sID);
        productMap.put("productState", "Not Approved");

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(SellerAddNewProductActivity.this, SellerHomeActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(SellerAddNewProductActivity.this, "Products is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(SellerAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
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