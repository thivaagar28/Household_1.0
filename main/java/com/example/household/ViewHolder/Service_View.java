package com.example.household.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.household.Interfacd.ItemClickListener;
import com.example.household.R;

public class Service_View extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView txtName, txtPhone;
    public ItemClickListener listener;


    public Service_View(@NonNull View itemView)
    {
        super(itemView);

        txtName =(TextView) itemView.findViewById(R.id.tv_Name);
        txtPhone =(TextView) itemView.findViewById(R.id.tv_number);

    }

    public void setItemClickListener(ItemClickListener listener){

        this.listener= listener;
    }

    @Override
    public void onClick(View v)
    {
        listener.onClick(v,getAdapterPosition(),false);
    }
}
