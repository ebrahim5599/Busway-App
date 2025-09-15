package com.sta.buswayapp.ui.boxing.worker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sta.buswayapp.R;

public class AddingNewBoxFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adding_new_box, container, false);

        NavOptions options = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build();

        view.findViewById(R.id.scan_bar_code_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"SCAN", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(AddingNewBoxFragment.this)
                        .navigate(R.id.addingNewItemsFragment, null, options);
            }
        });

        view.findViewById(R.id.scanBoxBarcodeOrRfidButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Scan Box Barcode Or RFID Button", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(AddingNewBoxFragment.this)
                        .navigate(R.id.addingNewItemsFragment, null, options);
            }
        });

        view.findViewById(R.id.postAndAddNewBox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"New Box", Toast.LENGTH_SHORT).show();
                // TODO 1: Post to backend

                // TODO 2: remove this page data

            }
        });

        view.findViewById(R.id.finishBoxing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Finish", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}