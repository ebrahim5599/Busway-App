package com.sta.buswayapp.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.sta.buswayapp.R;
import com.sta.buswayapp.model.ConstantNames;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends CustomerAdapter {

    private Fragment fragment;
    private String processName;

    public ProjectAdapter(Context context, ArrayList<String> customerNames) {
        super(context, customerNames);
    }

    public ProjectAdapter(Context context, ArrayList<String> customerNamesArrayList, Fragment fragment) {
        super(context, customerNamesArrayList, fragment);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sharedPreferences = fragment.getContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        processName = sharedPreferences.getString(ConstantNames.PROCESS, "def");
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        holder.customerNameCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (processName.equals(ConstantNames.BOXING)) {
                    if (sharedPreferences.getString(ConstantNames.TYPE_OF_USER, "def").equals(ConstantNames.WORKER))
                        NavHostFragment.findNavController(fragment)
                                .navigate(R.id.newOrEditBoxFragment, null, options);
                    else
                        NavHostFragment.findNavController(fragment)
                                .navigate(R.id.reviewCompletedBoxesFragment, null, options);

                } else if (processName.equals(ConstantNames.PACKING_CHECK)) {
                    NavHostFragment.findNavController(fragment)
                            .navigate(R.id.packingSupervisorSideFragment, null, options);
                }
            }
        });
    }
}
