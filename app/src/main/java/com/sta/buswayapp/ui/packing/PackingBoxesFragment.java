package com.sta.buswayapp.ui.packing;

import static android.content.Context.MODE_PRIVATE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sta.buswayapp.R;
import com.sta.buswayapp.adapter.PackedBoxAdapter;
import com.sta.buswayapp.databinding.FragmentPackingSupervisorSideBinding;
import com.sta.buswayapp.model.ConstantNames;
import com.sta.buswayapp.model.boxing.box.admin.boxItems.BoxedItemsData;
import com.sta.buswayapp.model.boxing.box.admin.boxItems.BoxedItemsResponse;
import com.sta.buswayapp.model.packing.PackedBoxesData;
import com.sta.buswayapp.model.packing.PackedBoxesResponse;
import com.sta.buswayapp.ui.guestView.GuestDataFragment;

import java.util.ArrayList;

public class PackingBoxesFragment extends Fragment {

    private FragmentPackingSupervisorSideBinding binding;
    private PackingBoxesViewModel viewModel;
    private PackedBoxAdapter packedBoxAdapter;
    private int boxNumber;
    private boolean atLeastOneBoxExist = true;
    private TextView boxBarcodeTextview;


    private final BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && "com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED".equals(intent.getAction())) {
                String barcodeData = intent.getStringExtra("data");
                if (binding.boxBarcodeTextview.getText() != null) {
                    binding.boxBarcodeTextview.setText(barcodeData);
                    viewModel.getBoxItemsByBarcod(barcodeData);
                }
                Toast.makeText(requireContext(), "Scanned: " + barcodeData, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPackingSupervisorSideBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(PackingBoxesViewModel.class);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        int projectId = Integer.parseInt(sharedPreferences.getString(ConstantNames.PROJECT_ID, "0"));

        NavOptions options = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build();



        ArrayList<PackedBoxesData> packedBoxArrayList = new ArrayList<>();
        viewModel.getPackedBoxes(projectId);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.completedPackingBoxesRecyclerView.setVisibility(View.GONE);

        packedBoxAdapter = new PackedBoxAdapter(getContext(), packedBoxArrayList, PackingBoxesFragment.this);
        binding.completedPackingBoxesRecyclerView.setAdapter(packedBoxAdapter);
        binding.completedPackingBoxesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        viewModel.getPackedBoxesMutableLiveData().observe(getViewLifecycleOwner(), new Observer<PackedBoxesResponse>() {
            @Override
            public void onChanged(PackedBoxesResponse packedBoxesResponse) {
                binding.progressBar.setVisibility(View.GONE);
                binding.completedPackingBoxesRecyclerView.setVisibility(View.VISIBLE);
                if (packedBoxesResponse == null){
                    Toast.makeText(getContext(), "Failed to get packed boxes.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "size " + packedBoxesResponse.data.size(), Toast.LENGTH_SHORT).show();
                    if (packedBoxesResponse.data.size() == 0) {
                        binding.productionSubmitPackingAdminView.setClickable(false);
                        binding.qualitySubmitPackingAdminView.setClickable(false);
                        binding.dispatchSubmitPackingAdminView.setClickable(false);
                    }
                    Toast.makeText(getContext(), packedBoxesResponse.message, Toast.LENGTH_SHORT).show();
                    packedBoxAdapter.setPackedBoxArrayList(packedBoxesResponse.data);
                }
            }
        });

        viewModel.getBoxedItemsMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BoxedItemsResponse>() {
            @Override
            public void onChanged(BoxedItemsResponse boxedItemsResponse) {
                if (boxedItemsResponse == null){
                    Toast.makeText(getContext(), "Failed to get response", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), boxedItemsResponse.message, Toast.LENGTH_SHORT).show();
                    if (boxedItemsResponse.isSucsess){
                        ArrayList<BoxedItemsData> boxedItemsData = boxedItemsResponse.data;
                        // send boxedItemsData
                        NavHostFragment.findNavController(PackingBoxesFragment.this)
                                .navigate(R.id.reviewCompletedItemsFragment, null, options);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavBackStackEntry backStackEntry =
                NavHostFragment.findNavController(this).getCurrentBackStackEntry();

        if (backStackEntry != null) {
            backStackEntry.getSavedStateHandle()
                    .<Integer>getLiveData(ConstantNames.READY_BOX_NUMBER)
                    .observe(getViewLifecycleOwner(), result -> {
                        boxNumber = result;
                        packedBoxAdapter.setReviewedBoxNumber(result);
                    });
        }
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