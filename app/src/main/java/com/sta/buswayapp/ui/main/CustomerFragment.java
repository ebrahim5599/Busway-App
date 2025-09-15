package com.sta.buswayapp.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sta.buswayapp.R;
import com.sta.buswayapp.adapter.CustomerAdapter;

import java.util.ArrayList;

public class CustomerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer, container, false);

        ArrayList<String> companies = new ArrayList<>();
        companies.add("Elsewedy");
        companies.add("ABB");
        companies.add("Emas");
        companies.add("STA");
        companies.add("LG");
        companies.add("Transformers");

        CustomerAdapter adapter = new CustomerAdapter(getContext(), companies, CustomerFragment.this);
        RecyclerView recyclerView = view.findViewById(R.id.customers_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}