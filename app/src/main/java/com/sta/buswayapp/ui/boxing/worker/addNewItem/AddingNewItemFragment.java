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

import android.util.Log;
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
import com.sta.buswayapp.model.boxing.item.Item;
import com.sta.buswayapp.model.boxing.item.Root;
import com.sta.buswayapp.model.boxing.item.ValidateItems;
import com.sta.buswayapp.model.boxing.item.modifyItem.ModifyItemData;
import com.sta.buswayapp.model.boxing.item.modifyItem.ModifyItemResponse;

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
    private String boxNumberAsText;

    private final BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && "com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED".equals(intent.getAction())) {
                String barcodeData = intent.getStringExtra("data");
                if (itemCodeArrayList != null && editItems) {
                    returnedItemList.add(new BoxedItemsData(barcodeData));
                    updatedItemsList.add(barcodeData);
//                    reviewAdapter.notifyDataSetChanged();
                } else if (itemCodeArrayList != null && !editItems) {
                    itemCodeArrayList.add(barcodeData);
//                    adapter.notifyDataSetChanged();
                }
                adapter.notifyDataSetChanged();
                // Toast.makeText(requireContext(), "Scanned: " + barcodeData, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddingNewItemsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Bundle bundle = getArguments();
        if (bundle != null) {
            editItems = bundle.getBoolean("edit_items_key");
            boxId = bundle.getInt("box_id");
            boxNumberAsText = bundle.getString("box_number_text", "");
            binding.boxNumberTextView.setText("Box No. " + boxId);
        }
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        int projectID = Integer.parseInt(sharedPreferences.getString(ConstantNames.PROJECT_ID, "0"));

        binding.projectNameTextView.setText("Project: " + sharedPreferences.getString(ConstantNames.PROJECT_NAME, "STA 1"));
        binding.projectSalesOrderTextView.setText("Sales order: " + sharedPreferences.getString(ConstantNames.SALES_ORDER, "99915050"));
        binding.boxNumberTextView.setText(boxNumberAsText);

        addNewItemViewModel = new ViewModelProvider(AddingNewItemFragment.this).get(AddNewItemViewModel.class);
        itemCodeArrayList = new ArrayList<>();
        updatedItemsList = new ArrayList<>();
        returnedItemList = new ArrayList<>();

        if (editItems) {
            adapter = new ScannedItemsAdapter(getContext(), updatedItemsList, AddingNewItemFragment.this);
//            reviewAdapter = new ReviewItemsAdapter(getContext(), returnedItemList, AddingNewItemFragment.this, true);
//            binding.completedPackingBoxesRecyclerView.setAdapter(reviewAdapter);

//            binding.loadingOverlay.setVisibility(View.VISIBLE);
//            addNewItemViewModel.getBoxItems(boxId);
//            // Get items inside the box.
//            addNewItemViewModel.getBoxedItemsResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BoxedItemsResponse>() {
//                @SuppressLint("NotifyDataSetChanged")
//                @Override
//                public void onChanged(BoxedItemsResponse boxedItemsResponse) {
//                    binding.loadingOverlay.setVisibility(View.GONE);
//                    binding.completedPackingBoxesRecyclerView.setVisibility(View.VISIBLE);
//                    if (boxedItemsResponse == null) {
//                        Toast.makeText(getContext(), "Failed to get items.", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getContext(), boxedItemsResponse.message, Toast.LENGTH_SHORT).show();
//                        if (boxedItemsResponse.isSucsess) {
//                            returnedItemList.addAll(boxedItemsResponse.data);
//                            reviewAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//            });

        } else {
            adapter = new ScannedItemsAdapter(getContext(), itemCodeArrayList, AddingNewItemFragment.this);
        }
        binding.completedPackingBoxesRecyclerView.setAdapter(adapter);
        binding.completedPackingBoxesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Validate Items button
        binding.validateItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loadingOverlay.setVisibility(View.VISIBLE);
                if (editItems) {
                    // TODO: LAST EDIT
                    // addNewItemViewModel.updateItemsList(boxId, updatedItemsList);
                    Log.i("TAG", "onClick: edit, " + boxId);
                    addNewItemViewModel.validateBoxItems(new ValidateItems(projectID, updatedItemsList, boxId));
                } else {
                    Log.i("TAG", "onClick: new");
                    addNewItemViewModel.validateBoxItems(new ValidateItems(projectID, itemCodeArrayList));
                }
            }
        });

        addNewItemViewModel.getItemValidationResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Root>() {
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
                                    .set(ConstantNames.ITEMS_LIST_KEY,
                                            editItems ? updatedItemsList : itemCodeArrayList);
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

    private ArrayList<ModifyItemData> convertToModifyItemList(ArrayList<Item> items) {
        ArrayList<ModifyItemData> modifyList = new ArrayList<>();
        for (Item item : items) {
            ModifyItemData modifyItem = new ModifyItemData();
            modifyItem.id = (int) item.id;
            modifyItem.barcode = item.barcode;
            modifyItem.index = item.index;
            modifyItem.reason = item.reason;
            modifyList.add(modifyItem);
        }
        return modifyList;
    }

    private ArrayList<String> convertToString(ArrayList<BoxedItemsData> boxedItemsData) {
        ArrayList<String> modifyList = new ArrayList<>();
        for (BoxedItemsData item : boxedItemsData) {
            modifyList.add(item.barcode);
        }
        return modifyList;
    }

}