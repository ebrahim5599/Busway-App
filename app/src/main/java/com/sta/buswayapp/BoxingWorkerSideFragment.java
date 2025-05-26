package com.sta.buswayapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class BoxingWorkerSideFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boxing_worker_side, container, false);

//        assert getArguments() != null;
//        String data = getArguments().getString("myKey");
//
//        Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
        return view;
    }
}