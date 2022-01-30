package com.example.household;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class ServiceLoginActivity extends AppCompatActivity {

    private EditText input_phone_number, input_password, input_field;
    private Button Login_Button;
    private ProgressDialog LoadingBar;
    private String parentDpName = "Service_Provider";
    private CheckBox chck_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_login);



        Login_Button = (Button) findViewById(R.id.login_button_login_page);
        input_phone_number = (EditText) findViewById(R.id.editTextPhone);
        input_password = (EditText) findViewById(R.id.editTextPassword);
        input_field = (EditText) findViewById(R.id.CreateTextField);
        LoadingBar = new ProgressDialog(this);

        chck_box = (CheckBox) findViewById(R.id.remember_me);
        Paper.init(this);

        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                LoginUser();
            }
        });



    }

    private void LoginUser()
    {
        String Phone_Number = input_phone_number.getText().toString();
        String Password = input_password.getText().toString();
        String Field = input_field.getText().toString();

        if(TextUtils.isEmpty(Phone_Number)){
            Toast.makeText(this, "Please write your phone number", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(Password)){
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Field)){
            Toast.makeText(this, "Please write your field", Toast.LENGTH_SHORT).show();
        }

        else {
            LoadingBar.setTitle("Login Account");
            LoadingBar.setMessage("Please wait, we are checking the credentials.");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();

            Access_to_the_account(Phone_Number,Password,Field);
        }
    }

    private void Access_to_the_account(String phone_number, String password, String field) {

        if(chck_box.isChecked())
        {
            Paper.book().write(ServicePrevalent.UserFieldKey,field);
            Paper.book().write(ServicePrevalent.UserPhoneKey,phone_number);
            Paper.book().write(ServicePrevalent.UserPasswordKey,password);


        }

        final DatabaseReference Rootref = FirebaseDatabase.getInstance("https://household-a7203-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(parentDpName).child(field).child(phone_number).exists())
                {
                    ServiceUsers usersData = snapshot.child(parentDpName).child(field).child(phone_number).getValue(ServiceUsers.class);




                    if(usersData.getPassword().equals(password))
                    {
                        Toast.makeText(ServiceLoginActivity.this, "Logged in successfully!!!", Toast.LENGTH_SHORT).show();
                        LoadingBar.dismiss();
                        Toast.makeText(ServiceLoginActivity.this, "Hello "+ usersData.getName()+" !!!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ServiceLoginActivity.this,Sevice_Plumbing_Activity2.class );

                        startActivity(intent);


                    }
                    else{
                        LoadingBar.dismiss();
                        Toast.makeText(ServiceLoginActivity.this, "Password is incorrect!!!", Toast.LENGTH_SHORT).show();

                    }


                }
                else{
                    Toast.makeText(ServiceLoginActivity.this, "Account with this "+ phone_number + " does not exist!!", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                    Toast.makeText(ServiceLoginActivity.this, "Please Sign Up to create an account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





}