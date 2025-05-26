package com.sta.buswayapp;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ProcessFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_process, container, false);
        assert getArguments() != null;
        String data = getArguments().getString("myKey");
        Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();

        TextView pageTitle = view.findViewById(R.id.process_page_title);
        pageTitle.setText("Customer: " + data);

        CardView boxingCardView = view.findViewById(R.id.boxingProcessCardView);
        boxingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Boxing", Toast.LENGTH_SHORT).show();

            }
        });

        CardView packingCardView = view.findViewById(R.id.packingProcessCardView);
        packingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "packing", Toast.LENGTH_SHORT).show();

            }
        });

        CardView dispatchingCardView = view.findViewById(R.id.dispatchingProcessCardView);
        dispatchingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "dispatching", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}