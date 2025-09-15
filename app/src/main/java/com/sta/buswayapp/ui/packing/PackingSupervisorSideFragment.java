package com.sta.buswayapp.ui.packing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sta.buswayapp.R;
import com.sta.buswayapp.adapter.PackedBoxAdapter;

import java.util.ArrayList;

public class PackingSupervisorSideFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_packing_supervisor_side, container, false);
        NavOptions options = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build();
        Button scanButton = view.findViewById(R.id.scanBoxBarcodeOrRfidButtonPackingProcess);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(PackingSupervisorSideFragment.this)
                        .navigate(R.id.packingSupervisorSideFragment, null, options);
            }
        });

        ArrayList<String> packedBoxArrayList = new ArrayList<>();
        packedBoxArrayList.add("Box 1");
        packedBoxArrayList.add("Box 2");
        packedBoxArrayList.add("Box 3");
        packedBoxArrayList.add("Box 4");
        packedBoxArrayList.add("Box 5");
        packedBoxArrayList.add("Box 6");
        packedBoxArrayList.add("Box 7");
        packedBoxArrayList.add("Box 8");
        packedBoxArrayList.add("Box 19");
        packedBoxArrayList.add("Box 20");
        packedBoxArrayList.add("Box 39");
        packedBoxArrayList.add("Box 79");
        packedBoxArrayList.add("Box 89");
        packedBoxArrayList.add("Box 100");

        PackedBoxAdapter packedBoxAdapter = new PackedBoxAdapter(getContext(), packedBoxArrayList, PackingSupervisorSideFragment.this);
        RecyclerView packedBoxRecyclerView = view.findViewById(R.id.completedPackingBoxesRecyclerView);
        packedBoxRecyclerView.setAdapter(packedBoxAdapter);
        packedBoxRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        return view;
    }
}