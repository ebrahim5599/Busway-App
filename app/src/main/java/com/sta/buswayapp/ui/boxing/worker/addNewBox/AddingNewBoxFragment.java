package com.sta.buswayapp.ui.boxing.worker.addNewBox;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sta.buswayapp.R;
import com.sta.buswayapp.model.ConstantNames;
import com.sta.buswayapp.model.box.CurrentBoxResponse;
import com.sta.buswayapp.model.box.UploadedBoxBody;
import com.sta.buswayapp.ui.guestView.GuestDataFragment;
import com.sta.buswayapp.ui.guestView.GuestDataViewModel;

import java.util.ArrayList;

public class AddingNewBoxFragment extends Fragment {
    private TextView boxBarcodeTextView, boxNumberTextView;
    private AddingNewBoxViewModel addingNewBoxViewModel;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressBar progressBar;
    private ArrayList<String> receivedList;
    private boolean navigateToAddNewBox = false;


    private final BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && "com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED".equals(intent.getAction())) {
                String barcodeData = intent.getStringExtra("data");
                if (boxBarcodeTextView != null) {
                    boxBarcodeTextView.setText(barcodeData);
                }
                Toast.makeText(requireContext(), "Scanned: " + barcodeData, Toast.LENGTH_SHORT).show();
            }
        }
    };
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adding_new_box, container, false);
        sharedPreferences = getContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        addingNewBoxViewModel = new ViewModelProvider(AddingNewBoxFragment.this).get(AddingNewBoxViewModel.class);

        progressBar = view.findViewById(R.id.boxNumberProgressBar);
        boxNumberTextView = view.findViewById(R.id.boxNumberTextView);
        boxBarcodeTextView = view.findViewById(R.id.scanBoxBarcodeTextView);
        TextView customerTextView = view.findViewById(R.id.boxingProcessCustomerName);
        TextView projectTextView = view.findViewById(R.id.boxingProcessProjectName);
        TextView salesOrderTextView = view.findViewById(R.id.boxingProcessSalesOrder);

        receivedList = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        boxNumberTextView.setVisibility(View.GONE);

        customerTextView.setText("Customer: " + sharedPreferences.getString(ConstantNames.CLIENT, "Elsewedy"));
        projectTextView.setText("Project: " + sharedPreferences.getString(ConstantNames.PROJECT_NAME, "STA 1"));
        salesOrderTextView.setText("Sales order: " + sharedPreferences.getString(ConstantNames.SALES_ORDER, "99915050"));

        NavOptions options = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build();

        view.findViewById(R.id.scan_bar_code_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"SCAN", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(AddingNewBoxFragment.this)
                        .navigate(R.id.addingNewItemsFragment, null, options);
            }
        });


        view.findViewById(R.id.postAndAddNewBox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"New Box", Toast.LENGTH_SHORT).show();
//                addingNewBoxViewModel.createNewBox(new UploadedBoxBody());
//                navigateToAddNewBox = true;
                // TODO 2: remove this page data

            }
        });

        view.findViewById(R.id.finishBoxing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Finish", Toast.LENGTH_SHORT).show();
//                addingNewBoxViewModel.createNewBox(new UploadedBoxBody());
//                navigateToAddNewBox = false;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED");
        requireContext().registerReceiver(scanReceiver, filter);
        addingNewBoxViewModel.getCurrentBoxNumber(sharedPreferences.getString(ConstantNames.PROJECT_ID, "0"));
        addingNewBoxViewModel.getBoxResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<CurrentBoxResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(CurrentBoxResponse currentBoxResponse) {
                progressBar.setVisibility(View.GONE);
                boxNumberTextView.setVisibility(View.VISIBLE);
                if (currentBoxResponse == null){
                    boxNumberTextView.setText("Failed to get box number");
                } else {
                    int boxNumber = currentBoxResponse.data.boxNumber;
                    boxNumberTextView.setText("Box No. " + boxNumber);
//                    editor.putInt(ConstantNames.BOX_NUMBER, boxNumber);
//                    editor.apply();
                }
            }
        });

        NavController navController = NavHostFragment.findNavController(this);

        if (navController.getCurrentBackStackEntry() != null) {
            SavedStateHandle handle = navController.getCurrentBackStackEntry().getSavedStateHandle();

            handle.<ArrayList<String>>getLiveData(ConstantNames.ITEMS_LIST_KEY)
                    .observe(getViewLifecycleOwner(), list -> {
                        Log.d("Result", "Received: " + list.toString());
                        receivedList.addAll(list);
                        Toast.makeText(getContext(), receivedList.get(0), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        requireContext().unregisterReceiver(scanReceiver);
    }

}