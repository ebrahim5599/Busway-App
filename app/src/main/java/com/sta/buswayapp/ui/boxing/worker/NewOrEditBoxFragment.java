package com.sta.buswayapp.ui.boxing.worker;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sta.buswayapp.R;
import com.sta.buswayapp.model.ConstantNames;
import com.sta.buswayapp.ui.main.ProcessFragment;

public class NewOrEditBoxFragment extends Fragment {

    private SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_or_edit_box, container, false);
        sharedPreferences = getContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);

        TextView customerName = view.findViewById(R.id.boxingProcessCustomerName);
        TextView projectName = view.findViewById(R.id.boxingProcessProjectName);
        TextView salesOrderNum = view.findViewById(R.id.boxingProcessSalesOrder);

        customerName.setText("Customer: " + sharedPreferences.getString(ConstantNames.CLIENT, "Elsewedy"));
        projectName.setText("Project: " + sharedPreferences.getString(ConstantNames.PROJECT_NAME, "STA 1"));
        salesOrderNum.setText("Sales order: " + sharedPreferences.getString(ConstantNames.SALES_ORDER, "99915050"));


        NavOptions options = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build();
        view.findViewById(R.id.editBox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(NewOrEditBoxFragment.this)
                        .navigate(R.id.boxingWorkerSideFragment, null, options);
            }
        });
        view.findViewById(R.id.addNewBox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(NewOrEditBoxFragment.this)
                        .navigate(R.id.boxingWorkerSideFragment, null, options);
            }
        });

        return view;
    }
}