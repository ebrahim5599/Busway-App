package com.sta.buswayapp.ui.boxing.worker.addNewItem;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sta.buswayapp.R;
import com.sta.buswayapp.adapter.ScannedItemsAdapter;
import com.sta.buswayapp.model.ConstantNames;

import com.sta.buswayapp.model.item.Item;
import com.sta.buswayapp.model.item.Root;
import com.sta.buswayapp.model.item.ValidateItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddingNewItemFragment extends Fragment {

    private ArrayList<String> itemCodeArrayList;
    private ScannedItemsAdapter adapter;
    private AddNewItemViewModel addNewItemViewModel;
    private RelativeLayout loadingOverlay;

    private final BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && "com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED".equals(intent.getAction())) {
                String barcodeData = intent.getStringExtra("data");
                if (itemCodeArrayList != null) {
                    itemCodeArrayList.add(barcodeData);
                    adapter.notifyDataSetChanged();
                }
                Toast.makeText(requireContext(), "Scanned: " + barcodeData, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adding_new_items, container, false);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        Button validateItemsButton = view.findViewById(R.id.validateItems);
        loadingOverlay = view.findViewById(R.id.loadingOverlay);

        TextView projectTextView = view.findViewById(R.id.projectNameTextView);
        TextView salesOrderTextView = view.findViewById(R.id.projectSalesOrderTextView);
        TextView boxNumberTextView = view.findViewById(R.id.boxNumberTextView);

        projectTextView.setText("Project: " + sharedPreferences.getString(ConstantNames.PROJECT_NAME, "STA 1"));
        salesOrderTextView.setText("Sales order: " + sharedPreferences.getString(ConstantNames.SALES_ORDER, "99915050"));
        boxNumberTextView.setText("Box No.: " + sharedPreferences.getInt(ConstantNames.BOX_NUMBER, 1));

        int projectID = Integer.parseInt(sharedPreferences.getString(ConstantNames.PROJECT_ID, "0"));

        addNewItemViewModel = new ViewModelProvider(AddingNewItemFragment.this).get(AddNewItemViewModel.class);
        itemCodeArrayList = new ArrayList<>();

        adapter = new ScannedItemsAdapter(getContext(), itemCodeArrayList, AddingNewItemFragment.this);

        RecyclerView recyclerView = view.findViewById(R.id.completedPackingBoxesRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        validateItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingOverlay.setVisibility(View.VISIBLE);
                addNewItemViewModel.validateBoxItems(new ValidateItems(projectID, itemCodeArrayList));
            }
        });

        addNewItemViewModel.getResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Root>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(Root root) {
                loadingOverlay.setVisibility(View.GONE);
                if (root == null) {
                    Toast.makeText(getContext(), "Failed to validate items.", Toast.LENGTH_SHORT).show();
                } else {
                    if (root.isSucsess) {
                        Toast.makeText(getContext(), root.message, Toast.LENGTH_SHORT).show();
                        NavController navController = NavHostFragment.findNavController(AddingNewItemFragment.this);
                        if (navController.getPreviousBackStackEntry() != null) {
                            navController.getPreviousBackStackEntry()
                                    .getSavedStateHandle()
                                    .set(ConstantNames.ITEMS_LIST_KEY, itemCodeArrayList);
                        }
                        navController.popBackStack();
                        adapter.setHasError(false);
                    } else {
                        adapter.setWrongItemArrayList(root.items);
                        adapter.setHasError(true);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED");
        requireContext().registerReceiver(scanReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        requireContext().unregisterReceiver(scanReceiver);
    }

}