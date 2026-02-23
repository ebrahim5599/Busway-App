package com.sta.buswayapp.ui.main;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sta.buswayapp.R;
import com.sta.buswayapp.model.ConstantNames;

public class ProcessFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_process, container, false);
        sharedPreferences = requireContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

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
                editor.putString(ConstantNames.PROCESS, ConstantNames.BOXING);
                editor.apply();
                NavHostFragment.findNavController(ProcessFragment.this)
                        .navigate(R.id.currentCustomersFragment, null, options);
            }
        });

        CardView packingCardView = view.findViewById(R.id.packingProcessCardView);
        packingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getString(ConstantNames.ROLE, "").equals(ConstantNames.WHITE_COLLAR)){
                    Toast.makeText(getContext(), "packing", Toast.LENGTH_SHORT).show();
                    editor.putString(ConstantNames.PROCESS, ConstantNames.PACKING_CHECK);
                    editor.apply();
                    NavHostFragment.findNavController(ProcessFragment.this)
                            .navigate(R.id.currentCustomersFragment, null, options);
                }else
                    Toast.makeText(getContext(), "Not allowed for your account", Toast.LENGTH_SHORT).show();
            }
        });

        CardView dispatchingCardView = view.findViewById(R.id.dispatchingProcessCardView);
        dispatchingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Not required for this phase.
                Toast.makeText(getContext(), "Dispatching is currently unavailable.", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), "dispatching", Toast.LENGTH_SHORT).show();
//                editor.putString(ConstantNames.PROCESS, ConstantNames.DISPATCHING);
//                editor.apply();
//                NavHostFragment.findNavController(ProcessFragment.this)
//                        .navigate(R.id.currentCustomersFragment, null, options);
            }
        });

        CardView scanItemQrCode = view.findViewById(R.id.scanItemQRCodeCardView);
        scanItemQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", sharedPreferences.getString(ConstantNames.ROLE, "user"));

                NavHostFragment.findNavController(ProcessFragment.this)
                        .navigate(R.id.guestScanFragment, bundle, options);
            }
        });

        CardView deliveredBoxes = view.findViewById(R.id.deliveredBoxesCardView);
        deliveredBoxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Not required for this phase.
                Toast.makeText(getContext(), "Delivered boxes is currently unavailable.", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), "delivered boxes", Toast.LENGTH_SHORT).show();
//                editor.putString(ConstantNames.PROCESS, ConstantNames.DELIVERED_BOXES);
//                editor.apply();
//                NavHostFragment.findNavController(ProcessFragment.this)
//                        .navigate(R.id.currentCustomersFragment, null, options);
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitDialog();
            }
        });
    }

    private void showExitDialog(){
        new AlertDialog.Builder(requireContext())
                .setTitle("تأكيد الخروج")
                .setMessage("هل أنت متأكد أنك تريد مغادرة التطبيق؟")
                .setPositiveButton("خروج", (dialog, which) -> {
                    requireActivity().finish();
                })
                .setNegativeButton("إلغاء", null)
                .show();
    }
}