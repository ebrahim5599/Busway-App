package com.sta.buswayapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sta.buswayapp.adapter.CompletedBoxAdapter;
import com.sta.buswayapp.model.BoxStatusModel;

import java.util.ArrayList;

public class BoxingSupervisorSideFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boxing_supervisor_side, container, false);

        ArrayList<BoxStatusModel> boxStatusModelArrayList = new ArrayList<>();
        boxStatusModelArrayList.add(new BoxStatusModel("Box1", "Completed"));
        boxStatusModelArrayList.add(new BoxStatusModel("Box2", "Completed"));
        boxStatusModelArrayList.add(new BoxStatusModel("Box3", "Not Started"));
        boxStatusModelArrayList.add(new BoxStatusModel("Box4", "In Progress"));
        boxStatusModelArrayList.add(new BoxStatusModel("Box5", "Completed"));

        CompletedBoxAdapter completedBoxAdapter = new CompletedBoxAdapter(getContext(), boxStatusModelArrayList, BoxingSupervisorSideFragment.this);
        RecyclerView recyclerView = view.findViewById(R.id.completedBoxesRecyclerView);
        recyclerView.setAdapter(completedBoxAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}