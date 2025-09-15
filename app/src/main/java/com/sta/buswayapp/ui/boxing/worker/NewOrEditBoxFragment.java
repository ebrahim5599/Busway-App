package com.sta.buswayapp.ui.boxing.worker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sta.buswayapp.R;
import com.sta.buswayapp.ui.main.ProcessFragment;

public class NewOrEditBoxFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_or_edit_box, container, false);

        NavOptions options = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build();
        view.findViewById(R.id.editBox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(NewOrEditBoxFragment.this)
                        .navigate(R.id.boxingWorkerSideFragment, null, options);
            }
        });
        view.findViewById(R.id.addNewBox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(NewOrEditBoxFragment.this)
                        .navigate(R.id.boxingWorkerSideFragment, null, options);
            }
        });

        return view;
    }
}