package com.example.household;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Service_Activity2 extends AppCompatActivity {

    private Button LOGIN, SIGNUP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service2);

        //Login Button
        LOGIN = (Button) findViewById(R.id.login_button);
        LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

        //SignUP
        SIGNUP = (Button) findViewById(R.id.signup_button);
        SIGNUP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openSignUpActivity2();
            }
        });
    }

    public void openLoginActivity(){  // Login Button
        Intent intent = new Intent(this, ServiceLoginActivity.class);
        startActivity(intent);
    }

    public void openSignUpActivity2(){  //Sign Up Button
        Intent intent = new Intent(this, ServiceSignUpActivity2.class);
        startActivity(intent);
    }

}
