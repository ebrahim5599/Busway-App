package com.sta.buswayapp.ui.boxing.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sta.buswayapp.R;
import com.sta.buswayapp.adapter.CompletedBoxWithCheckboxAdapter;
import com.sta.buswayapp.model.BoxStatusModel;

import java.util.ArrayList;

public class ReviewCompletedBoxesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review_completed_boxes, container, false);

        ArrayList<BoxStatusModel> completedBoxesForAdmin = new ArrayList<>();
        completedBoxesForAdmin.add(new BoxStatusModel("Box 1", "Completed"));
        completedBoxesForAdmin.add(new BoxStatusModel("Box 2", "Completed"));
        completedBoxesForAdmin.add(new BoxStatusModel("Box 3", "Completed"));
        completedBoxesForAdmin.add(new BoxStatusModel("Box 4", "Completed"));

        CompletedBoxWithCheckboxAdapter adapter = new CompletedBoxWithCheckboxAdapter(getContext(), completedBoxesForAdmin, ReviewCompletedBoxesFragment.this);
        RecyclerView recyclerView = view.findViewById(R.id.completedBoxesRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}