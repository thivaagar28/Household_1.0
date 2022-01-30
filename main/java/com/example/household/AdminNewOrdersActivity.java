package com.example.household;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.household.Model.AdminOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrdersActivity extends AppCompatActivity
{
    private DatabaseReference ordersRef;
    private RecyclerView ordersList;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);

        //ordersRef = FirebaseDatabase.getInstance().getReference("https://household-a7203-default-rtdb.asia-southeast1.firebasedatabase.app/").child("Orders");
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        ordersList = findViewById(R.id.orders_list);
        ordersList = findViewById(R.id.orders_list);
        layoutManager = new LinearLayoutManager(this);
        ordersList.setHasFixedSize(true);
        ordersList.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(ordersRef,AdminOrders.class)
                        .build();

        FirebaseRecyclerAdapter<AdminOrders,AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder adminOrdersViewHolder,int i, @NonNull AdminOrders adminOrders)
                    {
                        adminOrdersViewHolder.userName.setText("Name: " + adminOrders.getName());
                        adminOrdersViewHolder.userPhone.setText("Phone: " + adminOrders.getPhone());
                        adminOrdersViewHolder.userPrice.setText("Price: " + adminOrders.getFee());
                        adminOrdersViewHolder.userDatetime.setText("Service at: " + adminOrders.getDate() + " " +adminOrders.getTime());
                        adminOrdersViewHolder.userAddress.setText("Address: " + adminOrders.getAddress());
                        adminOrdersViewHolder.workerName.setText("Worker Name: " + adminOrders.getService_provider_name());

                        adminOrdersViewHolder.itemView.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {

                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Yes",
                                                "No"
                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrdersActivity.this);
                                builder.setTitle("Have you done this service and received payment ?");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if (i == 0)
                                        {
                                            String uID = getRef(i).getKey();

                                            RemoveOrder(uID);
                                        }

                                        else
                                        {
                                            finish();
                                        }
                                    }
                                });

                                builder.show();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return new AdminOrdersViewHolder(view);
                    }
                };
        ordersList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView userName, userPhone, userPrice, userAddress, userDatetime, workerName;
        public Button ShowOrdersBtn;

        public AdminOrdersViewHolder(View itemView)
        {
            super(itemView);

            userName = itemView.findViewById(R.id.order_user_name);
            userPhone = itemView.findViewById(R.id.order_phone_number);
            userPrice = itemView.findViewById(R.id.order_price);
            userAddress = itemView.findViewById(R.id.order_address);
            userDatetime = itemView.findViewById(R.id.order_date_time);
            workerName = itemView.findViewById(R.id.order_worker_name);

            //ShowOrdersBtn = itemView.findViewById(R.id.show_all_services_btn);


        }
    }

    private void RemoveOrder(String uID)
    {
        ordersRef.child(uID).removeValue();
    }
}