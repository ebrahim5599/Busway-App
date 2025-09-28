package com.sta.buswayapp.ui.boxing.worker;

import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.sta.buswayapp.R;
import com.sta.buswayapp.adapter.ScannedItemsAdapter;
import com.sta.buswayapp.model.item.ItemCode;

import java.util.ArrayList;

public class AddingNewItemsFragment extends Fragment {

    ArrayList<ItemCode> itemCodeArrayList;
    ScannedItemsAdapter adapter;

    private final BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && "com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED".equals(intent.getAction())) {
                String barcodeData = intent.getStringExtra("data");
                if (itemCodeArrayList != null) {
                    itemCodeArrayList.add(new ItemCode(barcodeData));
                    adapter.notifyDataSetChanged();
                }
                Toast.makeText(requireContext(), "Scanned: " + barcodeData, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adding_new_items, container, false);
        itemCodeArrayList = new ArrayList<>();
        adapter = new ScannedItemsAdapter(getContext(), itemCodeArrayList, AddingNewItemsFragment.this);


        RecyclerView recyclerView = view.findViewById(R.id.completedPackingBoxesRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        if (getArguments() != null) {
//            String pageTitle = getArguments().getString("title");
//            TextView textView = view.findViewById(R.id.guestPageTitle);
//            textView.setText(pageTitle);
//        }

//        resultTextView = view.findViewById(R.id.qrData);

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