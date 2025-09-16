package com.sta.buswayapp.ui.guestView;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sta.buswayapp.R;
import com.sta.buswayapp.data.APIInterface;
import com.sta.buswayapp.data.GuestDataBuilder;
import com.sta.buswayapp.model.ConstantNames;
import com.sta.buswayapp.model.GuestData;
import com.sta.buswayapp.ui.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GuestDataFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText fullNameEditText, companyNameEditText, phoneEditText, projectNameEditText, positionEditText;
    private GuestData guestData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guest_data, container, false);
        sharedPreferences = requireContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        GuestDataViewModel guestDataViewModel = new ViewModelProvider(GuestDataFragment.this).get(GuestDataViewModel.class);

        fullNameEditText = view.findViewById(R.id.guestNameEditText);
        companyNameEditText = view.findViewById(R.id.guestCompanyNameEditText);
        phoneEditText = view.findViewById(R.id.guestNumberEditText);
        projectNameEditText = view.findViewById(R.id.guestProjectNameEditText);
        positionEditText = view.findViewById(R.id.guestPositionEditText);

        Button nextButton = view.findViewById(R.id.nextButton);
        NavOptions options = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullNameEditText.getText().toString().trim();
                String companyName = companyNameEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String projectName = projectNameEditText.getText().toString().trim();
                String position = positionEditText.getText().toString().trim();

                if (name.isEmpty()) {
                    fullNameEditText.setError("Name is required");
                } else if (name.matches(".*\\d.*")) {
                    fullNameEditText.setError("Numbers are not allowed");
                } else if (phone.isEmpty()) {
                    phoneEditText.setError("Phone number is required");
                } else if (companyName.isEmpty()) {
                    companyNameEditText.setError("Company name is required");
                } else if (companyName.matches("^[0-9]+$")) {
                    companyNameEditText.setError("Please enter text, not numbers");
                } else if (projectName.isEmpty()) {
                    projectNameEditText.setError("Project name is required");
                } else if (projectName.matches("^[0-9]+$")) {
                    projectNameEditText.setError("Please enter text, not numbers");
                } else if (position.isEmpty()) {
                    positionEditText.setError("Position name is required");
                } else if (position.matches("^[0-9]+$")) {
                    positionEditText.setError("Please enter text, not numbers");
                } else {
                    guestData = new GuestData(name, phone, companyName, projectName, position);
                    editor.putString(ConstantNames.GUEST_NAME, guestData.getName());
                    editor.putString(ConstantNames.GUEST_PHONE_NUMBER, guestData.getPhoneNumber());
                    editor.putString(ConstantNames.GUEST_COMPANY, guestData.getCompany());
                    editor.putString(ConstantNames.GUEST_COMPANY_PROJECT, guestData.getProjectName());
                    editor.putString(ConstantNames.GUEST_POSITION, guestData.getPosition());

                    guestDataViewModel.postNewGuest(guestData);

                    editor.apply();
                }

                // TODO: ---------------------------------------------------------------------
                guestDataViewModel.getGuestDataMutableLiveData().observe(getViewLifecycleOwner(), new Observer<GuestData>() {
                    @Override
                    public void onChanged(GuestData guestData) {
                        if (guestData == null){
                            Toast.makeText(getContext(), "Failed to save guest data", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Guest data is added successfully", Toast.LENGTH_SHORT).show();
                        }
                        NavHostFragment.findNavController(GuestDataFragment.this)
                                .navigate(R.id.guestScanFragment, null, options);
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fullNameEditText.setText(sharedPreferences.getString(ConstantNames.GUEST_NAME, ""));
        phoneEditText.setText(sharedPreferences.getString(ConstantNames.GUEST_PHONE_NUMBER, ""));
        companyNameEditText.setText(sharedPreferences.getString(ConstantNames.GUEST_COMPANY, ""));
        projectNameEditText.setText(sharedPreferences.getString(ConstantNames.GUEST_COMPANY_PROJECT, ""));
        positionEditText.setText(sharedPreferences.getString(ConstantNames.GUEST_POSITION, ""));
    }
}