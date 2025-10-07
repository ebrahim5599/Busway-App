package com.sta.buswayapp.ui.main.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sta.buswayapp.R;
import com.sta.buswayapp.adapter.ProjectAdapter;
import com.sta.buswayapp.model.project.ProjectData;
import com.sta.buswayapp.model.project.ProjectResponse;
import com.sta.buswayapp.ui.main.customer.CustomerFragment;
import com.sta.buswayapp.ui.main.customer.CustomerViewModel;

import java.util.ArrayList;

public class ProjectFragment extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.projects_recyclerview);

        assert getArguments() != null;
        int clientId = getArguments().getInt("clientId");
        String clientName = getArguments().getString("clientName");
        
        TextView clientTextView = view.findViewById(R.id.customer_name);
        clientTextView.setText(clientName);

        ProjectViewModel projectViewModel = new ViewModelProvider(ProjectFragment.this).get(ProjectViewModel.class);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        projectViewModel.getClientData(String.valueOf(clientId));
        ArrayList<ProjectData> projects = new ArrayList<>();
        ProjectAdapter adapter = new ProjectAdapter(getContext(), projects, ProjectFragment.this);
        projectViewModel.projectResponseMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ProjectResponse>() {
            @Override
            public void onChanged(ProjectResponse projectResponse) {

                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (projectResponse == null){
                    Toast.makeText(getContext(), "Failed to get project data", Toast.LENGTH_SHORT).show();
                } else {
                    projects.clear();
                    projects.addAll(projectResponse.data);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}