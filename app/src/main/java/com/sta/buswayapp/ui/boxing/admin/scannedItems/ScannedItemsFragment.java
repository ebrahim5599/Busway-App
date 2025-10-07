package com.sta.buswayapp.ui.boxing.admin.scannedItems;

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

import com.sta.buswayapp.adapter.ReviewItemsAdapter;
import com.sta.buswayapp.adapter.ScannedItemsAdapter;
import com.sta.buswayapp.databinding.FragmentScannedItemsBinding;
import com.sta.buswayapp.model.ConstantNames;
import com.sta.buswayapp.model.box.admin.boxItems.BoxedItemsData;
import com.sta.buswayapp.model.box.admin.boxItems.BoxedItemsResponse;
import com.sta.buswayapp.ui.boxing.worker.addNewItem.AddingNewItemFragment;

import java.util.ArrayList;

public class ScannedItemsFragment extends Fragment {

    private FragmentScannedItemsBinding binding;
    private int boxId, boxNumber;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentScannedItemsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        ScannedItemsViewModel scannedItemsViewModel = new ViewModelProvider(this).get(ScannedItemsViewModel.class);
        ArrayList<BoxedItemsData> itemCodeArrayList = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null) {
            boxId = bundle.getInt(ConstantNames.BOX_ID);
            boxNumber = bundle.getInt(ConstantNames.BOX_NUMBER);
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.completedItemsRecyclerView.setVisibility(View.GONE);
            scannedItemsViewModel.getScannedItems(boxId);
            binding.boxNumberTextView.setText("Box " + boxNumber);
        }
        binding.projectNameTextView.setText(sharedPreferences.getString(ConstantNames.PROJECT_NAME, ""));
        binding.salesOrderTextField.setText(sharedPreferences.getString(ConstantNames.SALES_ORDER, ""));

        ReviewItemsAdapter adapter = new ReviewItemsAdapter(getContext(), itemCodeArrayList, ScannedItemsFragment.this);
        binding.completedItemsRecyclerView.setAdapter(adapter);
        binding.completedItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        scannedItemsViewModel.getBoxedItemsResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BoxedItemsResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(BoxedItemsResponse boxedItemsResponse) {
                binding.progressBar.setVisibility(View.GONE);
                binding.completedItemsRecyclerView.setVisibility(View.VISIBLE);
                if (boxedItemsResponse == null){
                    Toast.makeText(getContext(), "Failed to get the boxed items.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), boxedItemsResponse.message, Toast.LENGTH_SHORT).show();
                    if (boxedItemsResponse.isSucsess){
                        itemCodeArrayList.clear();
                        itemCodeArrayList.addAll(boxedItemsResponse.data);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        return view;
    }
}