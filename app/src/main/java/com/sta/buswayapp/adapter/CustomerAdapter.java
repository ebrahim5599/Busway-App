package com.sta.buswayapp.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sta.buswayapp.R;
import com.sta.buswayapp.model.ConstantNames;
import com.sta.buswayapp.model.client.ClientData;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private Context context;
    private ArrayList<ClientData> customerNamesArrayList;
    private Fragment fragment;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public CustomerAdapter(Context context, ArrayList<ClientData> customerNamesArrayList, Fragment fragment) {
        this.context = context;
        this.customerNamesArrayList = customerNamesArrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CustomerAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sharedPreferences = context.getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return new CustomerViewHolder(LayoutInflater.from(context).inflate(R.layout.one_item_container, parent, false));
    }

    NavOptions options = new NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build();

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.CustomerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.customerName.setText(customerNamesArrayList.get(position).name);
        holder.customerNameCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(ConstantNames.CLIENT, customerNamesArrayList.get(position).name);
                Toast.makeText(context, sharedPreferences.getString(ConstantNames.PROCESS, "default"), Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putInt("clientId", customerNamesArrayList.get(position).id);
                args.putString("clientName", customerNamesArrayList.get(position).name);
                editor.apply();
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.projectFragment, args, options);
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
            customerName = itemView.findViewById(R.id.one_item_text_view);
            customerNameCardView = itemView.findViewById(R.id.one_item_card_view);
        }
    }
}
