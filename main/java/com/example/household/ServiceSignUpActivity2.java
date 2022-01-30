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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ServiceSignUpActivity2 extends AppCompatActivity {

    private Button Create_account_Button;
    private EditText Input_name, Input_phone_number, Input_password,Input_Field;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_sign_up2);

        Create_account_Button = (Button) findViewById(R.id.signup_button_Signup_page);
        Input_name = (EditText) findViewById(R.id.CreateName);
        Input_phone_number = (EditText) findViewById(R.id.CreateTextPhone);
        Input_password = (EditText) findViewById(R.id.CreateTextPassword);
        Input_Field = (EditText) findViewById(R.id.CreateTextField);
        LoadingBar = new ProgressDialog(this);


        Create_account_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });

    }

    private void CreateAccount(){
        String Name = Input_name.getText().toString();
        String Phone_Number = Input_phone_number.getText().toString();
        String Password = Input_password.getText().toString();
        String Field = Input_Field.getText().toString();



        if(TextUtils.isEmpty(Name)){
            Toast.makeText(this, "Please write your name", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(Phone_Number)){
            Toast.makeText(this, "Please write your phone number", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(Password)){
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Field)){
            Toast.makeText(this, "Please write your field", Toast.LENGTH_SHORT).show();
        }
        else{
            LoadingBar.setTitle("Create Account");
            LoadingBar.setMessage("Please wait, we are checking the credentials.");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();


            Validate_PH_number(Name,Phone_Number,Password,Field);

        }


    }

    private void Validate_PH_number(String name, String phone_number, String password, String field) {



            final DatabaseReference Rootref = FirebaseDatabase.getInstance("https://household-a7203-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();


        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {




                if (!(snapshot.child("Service_Provider").child(field).child(phone_number).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone_number", phone_number);
                    userdataMap.put("name", name);
                    userdataMap.put("password", password);
                    userdataMap.put("fee", "RM 20");

                   /*if(field == "Plumber"){

                    }
                    else if(field=="Electrician"){
                        userdataMap.put("fee", "RM 20");
                    }
                    else if(field=="Pest"){
                        userdataMap.put("fee", "RM 30");
                    }else if(field=="Cleaner"){
                    userdataMap.put("fee", "RM 40");
                }else*/

                    Rootref.child("Service_Provider").child(field).child(phone_number).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(ServiceSignUpActivity2.this, "Congratulations, your account is created successfully" , Toast.LENGTH_SHORT).show();
                                        LoadingBar.dismiss();

                                        Intent intent = new Intent(ServiceSignUpActivity2.this,Service_Activity2.class );
                                        startActivity(intent);
                                    }
                                    else{
                                        LoadingBar.dismiss();
                                        Toast.makeText(ServiceSignUpActivity2.this, "Network Error, Please try agian!" , Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                }


                else
                {
                    Toast.makeText(ServiceSignUpActivity2.this, "This "+ phone_number + " is already exist!" , Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                    Toast.makeText(ServiceSignUpActivity2.this, "Please try again using different phone number." , Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ServiceSignUpActivity2.this,MainActivity.class );
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}