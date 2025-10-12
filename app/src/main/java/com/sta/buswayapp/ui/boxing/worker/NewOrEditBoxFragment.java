package com.sta.buswayapp.ui.boxing.worker;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sta.buswayapp.R;
import com.sta.buswayapp.adapter.ModifiedBoxAdapter;
import com.sta.buswayapp.databinding.FragmentNewOrEditBoxBinding;
import com.sta.buswayapp.model.ConstantNames;
import com.sta.buswayapp.model.box.worker.modifyBox.ModifyBoxData;
import com.sta.buswayapp.model.box.worker.modifyBox.ModifyBoxResponse;

import java.util.ArrayList;
import java.util.Objects;

public class NewOrEditBoxFragment extends Fragment {

    private FragmentNewOrEditBoxBinding binding;
    private SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewOrEditBoxBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        EditBoxViewModel editBoxViewModel = new ViewModelProvider(this).get(EditBoxViewModel.class);
        sharedPreferences = requireContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);

        binding.boxingProcessCustomerName.setText("Customer: " + sharedPreferences.getString(ConstantNames.CLIENT, "Elsewedy"));
        binding.boxingProcessProjectName.setText("Project: " + sharedPreferences.getString(ConstantNames.PROJECT_NAME, "STA 1"));
        binding.boxingProcessSalesOrder.setText("Sales order: " + sharedPreferences.getString(ConstantNames.SALES_ORDER, "99915050"));


        NavOptions options = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build();

        view.findViewById(R.id.addNewBox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(NewOrEditBoxFragment.this)
                        .navigate(R.id.boxingWorkerSideFragment, null, options);
            }
        });

        ArrayList<ModifyBoxData> modifiedBoxes = new ArrayList<>();
        ModifiedBoxAdapter adapter = new ModifiedBoxAdapter(getContext(), modifiedBoxes, NewOrEditBoxFragment.this);
        binding.returnedBoxesRecyclerView.setAdapter(adapter);
        binding.returnedBoxesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        editBoxViewModel.boxesNeedTobeModified(Integer.parseInt(sharedPreferences.getString(ConstantNames.PROJECT_ID, "0")));

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.returnedBoxesRecyclerView.setVisibility(View.GONE);
        editBoxViewModel.getModifyBoxResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ModifyBoxResponse>() {
            @Override
            public void onChanged(ModifyBoxResponse modifyBoxResponse) {
                binding.progressBar.setVisibility(View.GONE);
                binding.returnedBoxesRecyclerView.setVisibility(View.VISIBLE);
                if (modifyBoxResponse == null){
                    Toast.makeText(getContext(), "Failed to get boxes", Toast.LENGTH_SHORT).show();
                } else {
                    if (modifyBoxResponse.data.size() == 0)
                        binding.returnedBoxesTextView.setText("No boxes for this project need to be modified.");
                    modifiedBoxes.clear();
                    modifiedBoxes.addAll(modifyBoxResponse.data);
                    adapter.notifyDataSetChanged();
                }
            }
        });


        return view;
    }
}