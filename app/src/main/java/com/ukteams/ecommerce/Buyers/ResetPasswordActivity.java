package com.ukteams.ecommerce.Buyers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ukteams.ecommerce.Prevalent.Prevalent;
import com.ukteams.ecommerce.R;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity {
    private String check = "";
    private TextView pageTitle, titleQuestions;
    private EditText phoneNumber, question1, question2;
    private Button verifyButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        check = getIntent().getStringExtra("check");


       //initialization
        pageTitle = findViewById(R.id.page_title);
        titleQuestions = findViewById(R.id.title_questions);
        phoneNumber = findViewById(R.id.find_phone_number);
        question1 = findViewById(R.id.question_1);
        question2 = findViewById(R.id.question_2);
        verifyButton = findViewById(R.id.verify_btn);
    }



    @Override
    protected void onStart() {
        super.onStart();

        phoneNumber.setVisibility(View.GONE);


        if (check.equals("settings")) {

            pageTitle.setText("Set Questions");
            titleQuestions.setText("Please Set the Answers for the Following Security Questions");
          verifyButton.setText("Set");

            displayPreviousAnswers();


           verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    setAnswers();


                }
            });

        }
        else if (check.equals("login")) { //ie if he is coming from the loginActivity, clicked forgot password, because he has forgotten his password

            phoneNumber.setVisibility(View.VISIBLE);

            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    verifyUser();

                }
            });

        }
    }


    //setAnswers method
     private void setAnswers(){

         String answer1 = question1.getText().toString().toLowerCase();
         String answer2 = question2.getText().toString().toLowerCase();


         if(question1.equals("") && question2.equals("")){

             Toast.makeText(ResetPasswordActivity.this, "Please answer both questions.",Toast.LENGTH_LONG).show();
         }else{

             DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                     .child("Users")
                     .child(Prevalent.currentOnlineUser.getPhone());

             HashMap<String, Object> userdataMap = new HashMap<>();
             userdataMap.put("answer1", answer1);
             userdataMap.put("answer2", answer2);


             ref.child("Security Questions").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     if(task.isSuccessful()){

                         Toast.makeText(ResetPasswordActivity.this,"You have set the security questions successfully",Toast.LENGTH_LONG).show();

                         Intent intent = new Intent(ResetPasswordActivity.this, HomeActivity.class);
                         startActivity(intent);

                     }


                 }
             });
         }
     }


     //display previous answers from the database to the user
     private void displayPreviousAnswers(){

         DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                 .child("Users")
                 .child(Prevalent.currentOnlineUser.getPhone());


         ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 if(dataSnapshot.exists()){

                     //retrieve it from database
                     String ans1 = dataSnapshot.child("answer1").getValue().toString();
                     String ans2 = dataSnapshot.child("answer2").getValue().toString();

                    //then display to the user
                     question1.setText(ans1);
                     question2.setText(ans2);
                 }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

     }


     //verify user method
    private void verifyUser(){ //verify user using his phone number and set security question,
                               //mostly after forgetting his password, and clicked on, forgot password.


        final String phone = phoneNumber.getText().toString();
        final String answer1 = question1.getText().toString().toLowerCase();
        final String answer2 = question2.getText().toString().toLowerCase();

           if(!phone.equals("") && !answer1.equals("") && !answer2.equals("")){


               final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                       .child("Users")
                       .child(phone);


               ref.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       if(dataSnapshot.exists()){

                           String mPhone = dataSnapshot.child("phone").getValue().toString();

                           if(dataSnapshot.hasChild("Security Questions")){

                               String ans1 = dataSnapshot.child("Security Questions").child("answer1").getValue().toString();
                               String ans2 = dataSnapshot.child("Security Questions").child("answer2").getValue().toString();

                               if(!ans1.equals(answer1)){

                                   Toast.makeText(ResetPasswordActivity.this,"Your First Answer if wrong!",Toast.LENGTH_LONG).show();

                               }else if(!ans2.equals(answer2)){

                                   Toast.makeText(ResetPasswordActivity.this,"Your Second Answer if wrong!",Toast.LENGTH_LONG).show();
                               }else{


                                   AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                                   builder.setTitle("New Password");

                                   final EditText newPassword = new EditText(ResetPasswordActivity.this);
                                   newPassword.setHint("Write password here...");
                                   builder.setView(newPassword);

                                   builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialogInterface, int i) {

                                           if(!newPassword.getText().toString().equals("")){

                                               ref.child("password")
                                                       .setValue(newPassword.getText().toString())
                                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                           @Override
                                                           public void onComplete(@NonNull Task<Void> task) {

                                                               if(task.isSuccessful()){

                                                                   Toast.makeText(ResetPasswordActivity.this,"Password changed successfully",Toast.LENGTH_LONG).show();

                                                                   Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                                                   startActivity(intent);
                                                               }

                                                           }
                                                       });
                                           }

                                       }
                                   });

                                   builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialogInterface, int i) {

                                           dialogInterface.cancel();

                                       }
                                   });


                                   builder.show();


                               }

                           } else{

                               Toast.makeText(ResetPasswordActivity.this,"You have not set the security questions",Toast.LENGTH_LONG).show();

                           }

                       } else {
                           Toast.makeText(ResetPasswordActivity.this,"This phone number does not exist!",Toast.LENGTH_LONG).show();
                       }

                   }



                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });


           }else {

                  Toast.makeText(ResetPasswordActivity.this,"Please complete the form",Toast.LENGTH_LONG).show();
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
