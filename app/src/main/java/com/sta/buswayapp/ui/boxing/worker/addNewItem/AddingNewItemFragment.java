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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sta.buswayapp.adapter.ReviewItemsAdapter;
import com.sta.buswayapp.adapter.ScannedItemsAdapter;
import com.sta.buswayapp.databinding.FragmentAddingNewItemsBinding;
import com.sta.buswayapp.model.ConstantNames;

import com.sta.buswayapp.model.boxing.box.admin.boxItems.BoxedItemsData;
import com.sta.buswayapp.model.boxing.box.admin.boxItems.BoxedItemsResponse;
import com.sta.buswayapp.model.boxing.item.modifyItem.ModifyItemResponse;
import com.sta.buswayapp.model.boxing.item.Root;
import com.sta.buswayapp.model.boxing.item.ValidateItems;

import java.util.ArrayList;

public class AddingNewItemFragment extends Fragment {

    private FragmentAddingNewItemsBinding binding;
    private ArrayList<String> itemCodeArrayList, updatedItemsList;
    private ArrayList<BoxedItemsData> returnedItemList;
    private ScannedItemsAdapter adapter;
    private ReviewItemsAdapter reviewAdapter;
    private AddNewItemViewModel addNewItemViewModel;
    private boolean editItems = false;
    private int boxId;

    private final BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && "com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED".equals(intent.getAction())) {
                String barcodeData = intent.getStringExtra("data");
                if (itemCodeArrayList != null && editItems) {
                    returnedItemList.add(new BoxedItemsData(barcodeData));
                    updatedItemsList.add(barcodeData);
                    reviewAdapter.notifyDataSetChanged();
                } else if (itemCodeArrayList != null && !editItems) {
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
        binding = FragmentAddingNewItemsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Bundle bundle = getArguments();
        if (bundle != null) {
            editItems = bundle.getBoolean("edit_items_key");
            boxId = bundle.getInt("box_id");
        }
        Toast.makeText(getContext(), editItems + "", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        int projectID = Integer.parseInt(sharedPreferences.getString(ConstantNames.PROJECT_ID, "0"));

        binding.projectNameTextView.setText("Project: " + sharedPreferences.getString(ConstantNames.PROJECT_NAME, "STA 1"));
        binding.projectSalesOrderTextView.setText("Sales order: " + sharedPreferences.getString(ConstantNames.SALES_ORDER, "99915050"));
        binding.boxNumberTextView.setText("Box No.: " + sharedPreferences.getInt(ConstantNames.BOX_NUMBER, 1));

        addNewItemViewModel = new ViewModelProvider(AddingNewItemFragment.this).get(AddNewItemViewModel.class);
        itemCodeArrayList = new ArrayList<>();
        updatedItemsList = new ArrayList<>();
        returnedItemList = new ArrayList<>();

        if (editItems) {
            binding.validateItems.setText("UPDATE ITEMS");
            binding.loadingOverlay.setVisibility(View.VISIBLE);
            addNewItemViewModel.getBoxItems(boxId);
            reviewAdapter = new ReviewItemsAdapter(getContext(), returnedItemList, AddingNewItemFragment.this, true);
            binding.completedPackingBoxesRecyclerView.setAdapter(reviewAdapter);
            binding.completedPackingBoxesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            addNewItemViewModel.getItemResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ModifyItemResponse>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onChanged(ModifyItemResponse modifyItemResponse) {
                    binding.loadingOverlay.setVisibility(View.GONE);
                    if (modifyItemResponse == null) {
                        Toast.makeText(getContext(), "Failed to update items.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (modifyItemResponse.isSucsess) {
                            Toast.makeText(getContext(), modifyItemResponse.message, Toast.LENGTH_SHORT).show();
                            NavController navController = NavHostFragment.findNavController(AddingNewItemFragment.this);
                            if (navController.getPreviousBackStackEntry() != null) {
                                navController.getPreviousBackStackEntry()
                                        .getSavedStateHandle()
                                        .set(ConstantNames.UPDATED_ITEMS_LIST_KEY, updatedItemsList);
                            }
                            navController.popBackStack();
                            reviewAdapter.setHasError(false);
                        } else {
                            reviewAdapter.setWrongItemArrayList(modifyItemResponse.errors);
                            reviewAdapter.setHasError(true);
                        }
                        reviewAdapter.notifyDataSetChanged();
                    }
                }
            });
            addNewItemViewModel.getBoxedItemsResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BoxedItemsResponse>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onChanged(BoxedItemsResponse boxedItemsResponse) {
                    binding.loadingOverlay.setVisibility(View.GONE);
                    binding.completedPackingBoxesRecyclerView.setVisibility(View.VISIBLE);
                    if (boxedItemsResponse == null) {
                        Toast.makeText(getContext(), "Failed to get items.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), boxedItemsResponse.message, Toast.LENGTH_SHORT).show();
                        if (boxedItemsResponse.isSucsess) {
                            returnedItemList.addAll(boxedItemsResponse.data);
                            reviewAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        } else {
            adapter = new ScannedItemsAdapter(getContext(), itemCodeArrayList, AddingNewItemFragment.this);
            binding.completedPackingBoxesRecyclerView.setAdapter(adapter);
            binding.completedPackingBoxesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            addNewItemViewModel.getResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Root>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onChanged(Root root) {
                    binding.loadingOverlay.setVisibility(View.GONE);
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
        }

        binding.validateItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loadingOverlay.setVisibility(View.VISIBLE);
                if (editItems) {
                    addNewItemViewModel.updateItemsList(boxId, updatedItemsList);
                } else {
                    addNewItemViewModel.validateBoxItems(new ValidateItems(projectID, itemCodeArrayList));
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