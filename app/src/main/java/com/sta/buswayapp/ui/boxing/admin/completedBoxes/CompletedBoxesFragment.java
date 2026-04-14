package com.sta.buswayapp.ui.boxing.admin.completedBoxes;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sta.buswayapp.adapter.CompletedBoxAdapter;
import com.sta.buswayapp.databinding.FragmentCompletedBoxesBinding;
import com.sta.buswayapp.model.ConstantNames;
import com.sta.buswayapp.model.boxing.box.admin.SubmittedBoxes;
import com.sta.buswayapp.model.boxing.box.admin.completedBox.CompletedBoxData;
import com.sta.buswayapp.model.boxing.box.admin.completedBox.CompletedBoxResponse;

import java.util.ArrayList;

public class CompletedBoxesFragment extends Fragment {

    private FragmentCompletedBoxesBinding binding;
    private CompletedBoxesViewModel completedBoxesViewModel;
    private CompletedBoxAdapter adapter;
    private int projectId;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCompletedBoxesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        completedBoxesViewModel = new ViewModelProvider(this).get(CompletedBoxesViewModel.class);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        projectId = Integer.parseInt(sharedPreferences.getString(ConstantNames.PROJECT_ID, "0"));
        binding.customerName.setText(sharedPreferences.getString(ConstantNames.CLIENT, ""));


        ArrayList<CompletedBoxData> completedBoxesForAdmin = new ArrayList<>();
        adapter = new CompletedBoxAdapter(getContext(), completedBoxesForAdmin, CompletedBoxesFragment.this);

        binding.completedBoxesRecyclerView.setAdapter(adapter);
        binding.completedBoxesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.completedBoxesRecyclerView.setVisibility(View.GONE);
        completedBoxesViewModel.getCompletedBoxes(projectId);
        completedBoxesViewModel.getCompletedBoxResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<CompletedBoxResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(CompletedBoxResponse completedBoxResponse) {
                binding.progressBar.setVisibility(View.GONE);
                binding.completedBoxesRecyclerView.setVisibility(View.VISIBLE);
                if (completedBoxResponse == null){
                    Toast.makeText(getContext(), "Failed to get boxes.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), completedBoxResponse.message, Toast.LENGTH_SHORT).show();
                    if (completedBoxResponse.isSucsess){
                        completedBoxesForAdmin.clear();
                        completedBoxesForAdmin.addAll(completedBoxResponse.data);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        // After submitting the boxes
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> selectedIds = adapter.getSelectedBoxIds();
                if (selectedIds.isEmpty()) {
                    Toast.makeText(getContext(), "No boxes selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Selected IDs: " + selectedIds, Toast.LENGTH_SHORT).show();
                    completedBoxesViewModel.submitCompletedBoxesByAdmin(selectedIds);
                    binding.loadingOverlay.setVisibility(View.VISIBLE);
                }
            }
        });

        completedBoxesViewModel.getSubmittedBoxesMutableLiveData().observe(getViewLifecycleOwner(), new Observer<SubmittedBoxes>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(SubmittedBoxes submittedBoxes) {
                binding.loadingOverlay.setVisibility(View.GONE);
                if (submittedBoxes == null){
                    Toast.makeText(getContext(), "Failed to submit boxes.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), submittedBoxes.message, Toast.LENGTH_SHORT).show();
                    if (submittedBoxes.isSucsess){
                        completedBoxesViewModel.getCompletedBoxes(projectId);
                        adapter.clearSelections();
                    }
                }
            }
        });

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}