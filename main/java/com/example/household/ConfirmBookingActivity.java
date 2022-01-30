package com.example.household;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.household.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmBookingActivity extends AppCompatActivity
{
    private EditText nameEditText, phoneEditText, addressEditText, cityEditText,Service_provider_name;
    private Button confirmOrderButton;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);


        confirmOrderButton = (Button) findViewById(R.id.confirm_final_order_btn);
        nameEditText = (EditText) findViewById(R.id.Shipment_name);
        phoneEditText = (EditText) findViewById(R.id.Shipment_phoneNum);
        addressEditText = (EditText) findViewById (R.id.Shipment_address);
        cityEditText = (EditText) findViewById (R.id.Shipment_city);
        Service_provider_name = (EditText) findViewById (R.id.Service_pro_name);

        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Check();
            }
        });

    }

    private void Check()
    {

        String SerPro = Service_provider_name.getText().toString();

        if (TextUtils.isEmpty(nameEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your full name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your phone number", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your address", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cityEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your city", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Service_provider_name.getText().toString()))
        {
            Toast.makeText(this, "Please provide the service provider's name", Toast.LENGTH_SHORT).show();
        }
        else
            ConfirmOrder(SerPro);
    }

    private void ConfirmOrder(String ser_pro)
    {


        final String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance ();
        SimpleDateFormat  currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());


        final DatabaseReference ordersRef = FirebaseDatabase.getInstance("https://household-a7203-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()
                .child("Orders").child(ser_pro);


        HashMap<String, Object> orderMap = new HashMap<>();

        orderMap.put("name", nameEditText.getText().toString());
        orderMap.put("phone", phoneEditText.getText().toString());
        orderMap.put("address", addressEditText.getText().toString());
        orderMap.put("city", cityEditText.getText().toString());
        orderMap.put("date", saveCurrentDate);
        orderMap.put("time", saveCurrentTime);
        orderMap.put("state", "not shipped" );
        orderMap.put("service_provider_name", ser_pro );
        orderMap.put("fee", "RM 20" );


        ordersRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
               if(task.isSuccessful())
               {
                   FirebaseDatabase.getInstance().getReference("https://household-a7203-default-rtdb.asia-southeast1.firebasedatabase.app/")
                           .child("Users")
                           .removeValue()
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful())
                                   {

                                       Intent intent = new Intent(ConfirmBookingActivity.this, Home_user_Activity2.class);

                                       Toast.makeText(ConfirmBookingActivity.this, "Your service has been booked successfully.", Toast.LENGTH_SHORT).show();

                                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                       startActivity(intent);
                                       finish();
                                   }
                               }
                           });
               }

            }
        });

    }

}