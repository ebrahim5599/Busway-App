package com.sta.buswayapp.ui.main.login;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sta.buswayapp.R;
import com.sta.buswayapp.model.ConstantNames;
import com.sta.buswayapp.model.auth.BaseResponse;
import com.sta.buswayapp.model.auth.LoginRequest;
import com.sta.buswayapp.model.auth.UserDataResponse;

public class LoginFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int department;
    private RelativeLayout loadingOverlay;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        sharedPreferences = requireContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loadingOverlay = view.findViewById(R.id.loadingOverlay);

        EditText emailEditText = view.findViewById(R.id.emailEditText);
        EditText passwordEditText = view.findViewById(R.id.passwordEditText);
        department = 2;

        String emailFromSP = sharedPreferences.getString(ConstantNames.EMAIL,"");
        String passwordFromSP = sharedPreferences.getString(ConstantNames.PASSWORD,"");

        if (!emailFromSP.isEmpty() && !passwordFromSP.isEmpty()){
            emailEditText.setText(emailFromSP);
            passwordEditText.setText(passwordFromSP);
        }

        NavOptions options = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build();

        TextView guestLogin = view.findViewById(R.id.guestLoginButton);
        guestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.guestDataFragment, null, options);
            }
        });

        Button signInButton = view.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingOverlay.setVisibility(View.VISIBLE);

                loginViewModel.setLoginResponse(new LoginRequest(emailEditText.getText().toString(),
                        passwordEditText.getText().toString()));
            }
        });

        loginViewModel.loginResponseMutableLiveData.observe(getViewLifecycleOwner(), new Observer<BaseResponse<UserDataResponse>>() {
            @Override
            public void onChanged(BaseResponse<UserDataResponse> userDataBaseResponse) {
                loadingOverlay.setVisibility(View.GONE);
                if (userDataBaseResponse != null) {
                    Toast.makeText(getContext(), userDataBaseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    // save to sharedPreference
                    editor.putString(ConstantNames.TOKEN, userDataBaseResponse.getToken());
                    editor.putInt(ConstantNames.USER_ID, userDataBaseResponse.getUserData().getUserId());
                    editor.putString(ConstantNames.EMAIL, userDataBaseResponse.getUserData().getEmail());
                    editor.putString(ConstantNames.PASSWORD, passwordEditText.getText().toString());
                    editor.putString(ConstantNames.FULL_NAME, userDataBaseResponse.getUserData().getFullName());
                    editor.putString(ConstantNames.DEPARTMENT, userDataBaseResponse.getUserData().getDepartment().trim());
                    editor.putInt(ConstantNames.DEPARTMENT_CODE, userDataBaseResponse.getUserData().getDepartmentCode());
                    // check the role
                    if (userDataBaseResponse.getUserData().getRole().equals(ConstantNames.WHITE_COLLAR)) { // TODO: Remove <!> if exists
                        // White Collar
                        editor.putString(ConstantNames.ROLE, ConstantNames.WHITE_COLLAR);
                        NavHostFragment.findNavController(LoginFragment.this)
                                .navigate(R.id.processFragment, null, options);
                    } else {
                        // Blue Collar
                        editor.putString(ConstantNames.ROLE, ConstantNames.BLUE_COLLAR);
                        NavHostFragment.findNavController(LoginFragment.this)
                                .navigate(R.id.processFragment, null, options);
                    }
                    editor.apply();
                } else {
                    Toast.makeText(getContext(), "invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}