package com.sta.buswayapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sta.buswayapp.adapter.CustomerAdapter;
import com.sta.buswayapp.model.ConstantNames;

import java.util.ArrayList;

public class CurrentCustomersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_current_customers, container, false);

        ArrayList<String> companies = new ArrayList<>();
        companies.add("Elsewedy");
        companies.add("ABB");
        companies.add("Emas");
        companies.add("STA");
        companies.add("LG");
        companies.add("Transformers");

        CustomerAdapter adapter = new CustomerAdapter(getContext(), companies, CurrentCustomersFragment.this);
        RecyclerView recyclerView = view.findViewById(R.id.customers_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}