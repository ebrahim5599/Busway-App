package com.sta.buswayapp.ui.main.customer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sta.buswayapp.R;
import com.sta.buswayapp.adapter.CustomerAdapter;
import com.sta.buswayapp.model.client.ClientData;
import com.sta.buswayapp.model.client.ClientResponse;
import com.sta.buswayapp.ui.guestView.GuestDataFragment;
import com.sta.buswayapp.ui.guestView.GuestDataViewModel;

import java.util.ArrayList;

public class CustomerFragment extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        ArrayList<ClientData> client = new ArrayList<>();
        CustomerAdapter adapter = new CustomerAdapter(getContext(), client, CustomerFragment.this);
        recyclerView = view.findViewById(R.id.customers_recyclerview);

        progressBar = view.findViewById(R.id.progressBar);
        CustomerViewModel customerViewModel = new ViewModelProvider(CustomerFragment.this).get(CustomerViewModel.class);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        customerViewModel.getClientData();
        customerViewModel.clientResponseMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ClientResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(ClientResponse clientResponse) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (clientResponse == null){
                    Toast.makeText(getContext(), "Failed to get client data", Toast.LENGTH_SHORT).show();
                } else {
                    client.clear();
                    client.addAll(clientResponse.data);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}