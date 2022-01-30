package com.example.household;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.household.Model.Category;
import com.example.household.ViewHolder.Service_View;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Pest_ControlActivity2 extends AppCompatActivity {



    private DatabaseReference ServiceRef;
    private RecyclerView recyclerView;
    public Button booking;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pest_control2);

        ServiceRef = FirebaseDatabase.getInstance().getReference().child("Service_Provider").child("Pest");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }


    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<Category> options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(ServiceRef, Category.class)
                        .build();


        FirebaseRecyclerAdapter<Category, Service_View> adapter=
                new FirebaseRecyclerAdapter<Category, Service_View>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull Service_View service_view, int i, @NonNull Category category) {
                        service_view.txtName.setText(category.getName());
                        service_view.txtPhone.setText(category.getPhone_number());
                        service_view.txtfee.setText(category.getFee());
                    }

                    @NonNull
                    @Override
                    public Service_View onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent,false);
                        Service_View holder = new Service_View(view);

                        booking = view.findViewById(R.id.button_chk1); //plumbing
                        booking.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {

                                Intent intent = new Intent(Pest_ControlActivity2.this,ConfirmBookingActivity.class);
                                startActivity(intent);
                            }
                        });

                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }



}
