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
import com.sta.buswayapp.adapter.ProjectAdapter;

import java.util.ArrayList;

public class ProjectFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project, container, false);

        ArrayList<String> projects = new ArrayList<>();
        projects.add("STA2 / 125451154545");
        projects.add("STA1 / 151551515154");
        projects.add("STA2 / 194515454551");
        projects.add("STA3 / 586515515515");

        ProjectAdapter adapter = new ProjectAdapter(getContext(), projects, ProjectFragment.this);
        RecyclerView recyclerView = view.findViewById(R.id.projects_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}