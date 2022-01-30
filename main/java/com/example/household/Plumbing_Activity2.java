package com.example.household;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.household.Model.Category;
import com.example.household.Model.Users;
import com.example.household.Prevalent.Prevalent;
import com.example.household.ViewHolder.Service_View;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Plumbing_Activity2 extends AppCompatActivity {

private DatabaseReference ServiceRef;
private RecyclerView recyclerView;
RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumbing2);

       // ServiceRef = FirebaseDatabase.getInstance("https://household-a7203-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        ServiceRef = FirebaseDatabase.getInstance().getReference().child("Service_Provider").child("Plumber");

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
                    }
                    @NonNull
                    @Override
                    public Service_View onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent,false);
                        Service_View holder = new Service_View(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
