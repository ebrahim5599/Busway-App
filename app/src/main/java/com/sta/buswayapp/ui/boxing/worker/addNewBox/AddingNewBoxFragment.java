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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sta.buswayapp.R;
import com.sta.buswayapp.databinding.FragmentAddingNewBoxBinding;
import com.sta.buswayapp.model.ConstantNames;
import com.sta.buswayapp.model.boxing.box.admin.ReturnedBox.ReturnedBoxResponse;
import com.sta.buswayapp.model.boxing.box.worker.getBoxNum.CurrentBoxResponse;
import com.sta.buswayapp.model.boxing.box.worker.createBox.CreatedBoxBody;
import com.sta.buswayapp.model.boxing.box.worker.createBox.CreatedBoxResponse;

import java.util.ArrayList;

public class AddingNewBoxFragment extends Fragment {
    private TextView boxBarcodeTextView, boxNumberTextView, itemInfoTextView;
    private AddingNewBoxViewModel addingNewBoxViewModel;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressBar progressBar;
    private ArrayList<String> receivedList;
    private String boxBarcodeText, boxLength, boxHeight, boxWidth, boxDimensions;
    private int projectID;
    private int boxWeight;
    private boolean navigateToCustomerFragment = false;
    private boolean editItemsFlag = false;
    private int boxId;
    private FragmentAddingNewBoxBinding binding;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddingNewBoxBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        sharedPreferences = requireContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        projectID = Integer.parseInt(sharedPreferences.getString(ConstantNames.PROJECT_ID, "0"));
        addingNewBoxViewModel = new ViewModelProvider(AddingNewBoxFragment.this).get(AddingNewBoxViewModel.class);

        progressBar = view.findViewById(R.id.boxNumberProgressBar);
        boxNumberTextView = view.findViewById(R.id.boxNumberTextView);
        boxBarcodeTextView = view.findViewById(R.id.scanBoxBarcodeTextView);
        TextView customerTextView = view.findViewById(R.id.boxingProcessCustomerName);
        TextView projectTextView = view.findViewById(R.id.boxingProcessProjectName);
        EditText boxWeightEditText = view.findViewById(R.id.boxWeightEditText);

        EditText boxLengthEditText = view.findViewById(R.id.boxLengthEditText);
        EditText boxWidthEditText = view.findViewById(R.id.boxWidthEditText);
        EditText boxHeightEditText = view.findViewById(R.id.boxHeightEditText);

        TextView salesOrderTextView = view.findViewById(R.id.boxingProcessSalesOrder);
        itemInfoTextView = view.findViewById(R.id.itemInfoTextView);
        itemInfoTextView.setVisibility(View.GONE);

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

        binding.scanBarCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"SCAN", Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putBoolean("edit_items_key", editItemsFlag);
                args.putInt("box_id", boxId);
                args.putString("box_number_text", boxNumberTextView.getText().toString());
                NavHostFragment.findNavController(AddingNewBoxFragment.this)
                        .navigate(R.id.addingNewItemsFragment, args, options);
            }
        });

        binding.postAndAddNewBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"New Box", Toast.LENGTH_SHORT).show();
                navigateToCustomerFragment = false;

                boxBarcodeText = boxBarcodeTextView.getText().toString();
                boxWeight = Integer.parseInt(boxWeightEditText.getText().toString());
                boxLength = boxLengthEditText.getText().toString();
                boxWidth = boxWidthEditText.getText().toString();
                boxHeight = boxHeightEditText.getText().toString();
                boxDimensions = boxLength + "×" + boxWidth + "×" + boxHeight;

                addingNewBoxViewModel.createNewBox(new CreatedBoxBody(boxBarcodeText,"", boxWeight, boxDimensions, projectID, receivedList ));
            }
        });

        binding.finishBoxing.findViewById(R.id.finishBoxing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Finish", Toast.LENGTH_SHORT).show();
                navigateToCustomerFragment = true;

                boxBarcodeText = boxBarcodeTextView.getText().toString();
                boxWeight = Integer.parseInt(boxWeightEditText.getText().toString());
                boxLength = boxLengthEditText.getText().toString();
                boxWidth = boxWidthEditText.getText().toString();
                boxHeight = boxHeightEditText.getText().toString();
                boxDimensions = boxLength + "×" + boxWidth + "×" + boxHeight;

                addingNewBoxViewModel.createNewBox(new CreatedBoxBody(boxBarcodeText,"", boxWeight, boxDimensions, projectID, receivedList ));
            }
        });

        addingNewBoxViewModel.getUploadedBoxResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<CreatedBoxResponse>() {
            @Override
            public void onChanged(CreatedBoxResponse uploadedBoxResponse) {
                if (uploadedBoxResponse == null) {
                    Toast.makeText(getContext(), "Failed to create box.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), uploadedBoxResponse.message, Toast.LENGTH_SHORT).show();
                    if (uploadedBoxResponse.isSuccess){
                        if (navigateToCustomerFragment){
                            NavHostFragment.findNavController(AddingNewBoxFragment.this).popBackStack(R.id.currentCustomersFragment, false);
                        } else {
                            boxWeightEditText.setText(null);
                            boxBarcodeTextView.setText(null);
                            boxLengthEditText.setText(null);
                            boxWidthEditText.setText(null);
                            boxHeightEditText.setText(null);
                            receivedList.clear();
                            itemInfoTextView.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                            boxNumberTextView.setVisibility(View.GONE);
                            addingNewBoxViewModel.getCurrentBoxNumber(sharedPreferences.getString(ConstantNames.PROJECT_ID, "0"));
                        }
                    }

                }
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            boxId = bundle.getInt("boxId");
            editItemsFlag = true;
            binding.scanBarCodeButton.setText("Show Box Items");
            binding.finishBoxing.setVisibility(View.GONE);
            binding.postAndAddNewBox.setVisibility(View.GONE);
            Toast.makeText(getContext(), "BOX ID: " + boxId, Toast.LENGTH_SHORT).show();
            addingNewBoxViewModel.returnedBoxesData(boxId);
            addingNewBoxViewModel.getReturnedBoxResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ReturnedBoxResponse>() {
                @Override
                public void onChanged(ReturnedBoxResponse returnedBoxResponse) {
                    progressBar.setVisibility(View.GONE);
                    boxNumberTextView.setVisibility(View.VISIBLE);

                    if (returnedBoxResponse == null) {
                        Toast.makeText(getContext(), "Failed to get box data.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (returnedBoxResponse.isSucsess){
                            boxNumberTextView.setText("Box No. " + returnedBoxResponse.data.boxNumber);
                            boxWeightEditText.setText(String.valueOf(returnedBoxResponse.data.weight));
                            boxWeightEditText.setEnabled(false);
                            if (returnedBoxResponse.data.dimension.contains("×")){
                                String[] dimensions = returnedBoxResponse.data.dimension.split("×");
                                boxLengthEditText.setText(dimensions[0]);
                                boxLengthEditText.setEnabled(false);
                                boxWidthEditText.setText(dimensions[1]);
                                boxWidthEditText.setEnabled(false);
                                boxHeightEditText.setText(dimensions[2]);
                                boxHeightEditText.setEnabled(false);
                            }
                            boxBarcodeTextView.setText(returnedBoxResponse.data.barCode);
                        }

                    }
                }
            });
        } else {
            editItemsFlag = false;

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
//                        editor.putInt(ConstantNames.BOX_NUMBER_WORKER, boxNumber);
//                        editor.apply();
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED");
        requireContext().registerReceiver(scanReceiver, filter);


        NavController navController = NavHostFragment.findNavController(this);

        if (navController.getCurrentBackStackEntry() != null) {
            SavedStateHandle handle = navController.getCurrentBackStackEntry().getSavedStateHandle();

            handle.<ArrayList<String>>getLiveData(ConstantNames.ITEMS_LIST_KEY)
                    .observe(getViewLifecycleOwner(), list -> {
                        receivedList.addAll(list);
                        itemInfoTextView.setVisibility(View.VISIBLE);
                    });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        requireContext().unregisterReceiver(scanReceiver);
    }

}