package com.example.household;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;


import io.paperdb.Paper;


public class Home_user_Activity2 extends AppCompatActivity{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
     Button plumb, electrical, house_clean, pest; // button variable for the service selecting

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user2);


        //Buttons
       plumb = (Button) findViewById(R.id.plumbing); //plumbing
       plumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Home_user_Activity2.this,Plumbing_Activity2.class);
                startActivity((intent));
            }
        });

        electrical = (Button) findViewById(R.id.electrical);// electrical
        electrical.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Home_user_Activity2.this,Electrical_Service_Activity2.class);
                startActivity((intent));
            }
        });

        house_clean = (Button) findViewById(R.id.House_cleaning);  //house cleaning
        house_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Home_user_Activity2.this,House_Cleaning_Activity2.class);
                startActivity((intent));
            }
        });

        pest = (Button) findViewById(R.id.Pest_control);  //pest control
        pest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Home_user_Activity2.this,Pest_ControlActivity2.class);
                startActivity((intent));
            }
        });

        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

                        Intent intent = new Intent(Home_user_Activity2.this, Home_user_Activity2.class);
                        startActivity(intent);
                        break;

//Paste your privacy policy link

//                    case  R.id.nav_Policy:{
//
//                        Intent browserIntent  = new Intent(Intent.ACTION_VIEW , Uri.parse(""));
//                        startActivity(browserIntent);
//
//                    }
                    //       break;
                    case R.id.logout_button_logout_page: // logout option for the user
                        Paper.book().destroy();
                        Intent logout_intent = new Intent(Home_user_Activity2.this, MainActivity.class);
                        startActivity((logout_intent));
                        break;


                    case R.id.nav_share: { // sharing the app to others through online

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "http://play.google.com/store/apps/detail?id=" + getPackageName();
                        String shareSub = "Try now";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));
                    }
                    break;
                }
                return false;
            }
        });


    }

    public void setUpToolbar(){
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
    }
