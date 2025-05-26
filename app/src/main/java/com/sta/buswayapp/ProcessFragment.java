package com.sta.buswayapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sta.buswayapp.model.ConstantNames;

public class ProcessFragment extends Fragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_process, container, false);
        sharedPreferences = requireContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Retrieve
        String client = sharedPreferences.getString(ConstantNames.CLIENT, "elsewedy");
        String user = sharedPreferences.getString(ConstantNames.TYPE_OF_USER, ConstantNames.WORKER);

        TextView pageTitle = view.findViewById(R.id.process_page_title);
        pageTitle.setText("Customer: " + client);

        NavOptions options = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build();

        CardView boxingCardView = view.findViewById(R.id.boxingProcessCardView);
        boxingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Boxing", Toast.LENGTH_SHORT).show();
                if (user.equals(ConstantNames.SUPERVISOR))
                    NavHostFragment.findNavController(ProcessFragment.this)
                        .navigate(R.id.boxingSupervisorSideFragment, null, options);
                else
                    NavHostFragment.findNavController(ProcessFragment.this)
                            .navigate(R.id.boxingWorkerSideFragment, null, options);
            }
        });

        CardView packingCardView = view.findViewById(R.id.packingProcessCardView);
        packingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "packing", Toast.LENGTH_SHORT).show();
                if (user.equals(ConstantNames.SUPERVISOR))
                    NavHostFragment.findNavController(ProcessFragment.this)
                            .navigate(R.id.packingSupervisorSideFragment, null, options);
                else
                    NavHostFragment.findNavController(ProcessFragment.this)
                            .navigate(R.id.packingWorkerSideFragment, null, options);
            }
        });

        CardView dispatchingCardView = view.findViewById(R.id.dispatchingProcessCardView);
        dispatchingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "dispatching", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}