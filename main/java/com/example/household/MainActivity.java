package com.example.household;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.household.Model.ServiceUsers;
import com.example.household.Model.Users;
import com.example.household.Prevalent.Prevalent;
import com.example.household.Prevalent.ServicePrevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
{

    private Button Getstarted,ServiceProvider;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadingBar = new ProgressDialog(this);

        Paper.init(this);
        ServiceProvider=(Button) findViewById(R.id.servicebutton);
        ServiceProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Service_Activity2.class);
                startActivity((intent));
            }
        });

        Getstarted = (Button) findViewById(R.id.button1);
        Getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });


        String ServicePhoneKey = Paper.book().read(ServicePrevalent.UserPhoneKey);
        String ServicePasswordKey = Paper.book().read(ServicePrevalent.UserPasswordKey);
        String ServiceFieldKey = Paper.book().read(ServicePrevalent.UserFieldKey);
        if(ServiceFieldKey != "" && ServicePasswordKey != "" && ServicePhoneKey != "")
        {
            if(!TextUtils.isEmpty(ServiceFieldKey) && !TextUtils.isEmpty(ServicePhoneKey) && !TextUtils.isEmpty(ServicePasswordKey))
            {
                ServiceAllowAcces(ServicePhoneKey,ServicePasswordKey,ServiceFieldKey);
                LoadingBar.setTitle("Already Logged in Account");
                LoadingBar.setMessage("Please wait, we are checking the credentials.");
                LoadingBar.setCanceledOnTouchOutside(false);
                LoadingBar.show();
            }
        }







        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);
        if(UserPhoneKey != "" && UserPasswordKey != "")
        {
            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey))
            {
                AllowAcces(UserPhoneKey,UserPasswordKey);
                LoadingBar.setTitle("Already Logged in Account");
                LoadingBar.setMessage("Please wait, we are checking the credentials.");
                LoadingBar.setCanceledOnTouchOutside(false);
                LoadingBar.show();
            }
        }


    }



    private void AllowAcces(final String phone_number, final String password) {
        final DatabaseReference Rootref = FirebaseDatabase.getInstance("https://household-a7203-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child("Users").child(phone_number).exists())
                {
                    Users usersData = snapshot.child("Users").child(phone_number).getValue(Users.class);




                    if(usersData.getPassword().equals(password))
                    {
                        Toast.makeText(MainActivity.this, "Logged in successfully!!!", Toast.LENGTH_SHORT).show();
                        LoadingBar.dismiss();
                        Toast.makeText(MainActivity.this, "Hello "+ usersData.getName()+" !!!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this,Home_user_Activity2.class );
                        startActivity(intent);


                    }
                    else{
                        LoadingBar.dismiss();
                        Toast.makeText(MainActivity.this, "Password is incorrect!!!", Toast.LENGTH_SHORT).show();

                    }


                }
                else{
                    Toast.makeText(MainActivity.this, "Account with this "+ phone_number + " does not exist!!", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Please Sign Up to create an account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    private void ServiceAllowAcces(String phone_number, String password, String field) {

        final DatabaseReference Rootref = FirebaseDatabase.getInstance("https://household-a7203-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child("Service_Provider").child(field).child(phone_number).exists())
                {
                    ServiceUsers usersData = snapshot.child("Service_Provider").child(field).child(phone_number).getValue(ServiceUsers.class);




                    if(usersData.getPassword().equals(password))
                    {
                        Toast.makeText(MainActivity.this, "Logged in successfully!!!", Toast.LENGTH_SHORT).show();
                        LoadingBar.dismiss();
                        Toast.makeText(MainActivity.this, "Hello "+ usersData.getName()+" !!!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this,Sevice_Plumbing_Activity2.class );

                        startActivity(intent);



                    }
                    else{
                        LoadingBar.dismiss();
                        Toast.makeText(MainActivity.this, "Password is incorrect!!!", Toast.LENGTH_SHORT).show();

                    }


                }
                else{
                    Toast.makeText(MainActivity.this, "Account with this "+ phone_number + " does not exist!!", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Please Sign Up to create an account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    public void openActivity2(){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
}