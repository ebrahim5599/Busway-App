package com.sta.buswayapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private Context context;
    private ArrayList<String> customerNamesArrayList;
    private Fragment fragment;

    public CustomerAdapter(Context context, ArrayList<String> customerNames) {
        this.context = context;
        this.customerNamesArrayList = customerNames;
    }

    public CustomerAdapter(Context context, ArrayList<String> customerNamesArrayList, Fragment fragment) {
        this.context = context;
        this.customerNamesArrayList = customerNamesArrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CustomerAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerViewHolder(LayoutInflater.from(context).inflate(R.layout.customers_container, parent, false));
    }

    NavOptions options = new NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build();

    Bundle bundle = new Bundle();

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.CustomerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.customerName.setText(customerNamesArrayList.get(position));
        holder.customerNameCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("myKey", customerNamesArrayList.get(position));
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.processFragment, bundle, options);
            }
        });


    }

    @Override
    public int getItemCount() {
        return customerNamesArrayList.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView customerName;
        CardView customerNameCardView;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customer_name);
            customerNameCardView = itemView.findViewById(R.id.customer_name_cardView);
        }
    }
}
