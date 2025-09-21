package com.sta.buswayapp.ui.main.customer;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sta.buswayapp.R;
import com.sta.buswayapp.adapter.CustomerAdapter;
import com.sta.buswayapp.model.client.ClientData;
import com.sta.buswayapp.model.client.ClientResponse;
import com.sta.buswayapp.ui.guestView.GuestDataFragment;
import com.sta.buswayapp.ui.guestView.GuestDataViewModel;

import java.util.ArrayList;

public class CustomerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        ArrayList<ClientData> client = new ArrayList<>();
        CustomerAdapter adapter = new CustomerAdapter(getContext(), client, CustomerFragment.this);

        CustomerViewModel customerViewModel = new ViewModelProvider(CustomerFragment.this).get(CustomerViewModel.class);
        customerViewModel.getClientData();
        customerViewModel.clientResponseMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ClientResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(ClientResponse clientResponse) {
                if (clientResponse == null){
                    Toast.makeText(getContext(), "Failed to get client data", Toast.LENGTH_SHORT).show();
                } else {
                    client.addAll(clientResponse.data);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.customers_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}