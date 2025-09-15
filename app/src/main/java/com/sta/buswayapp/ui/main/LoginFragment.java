package com.sta.buswayapp.ui.main;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sta.buswayapp.R;
import com.sta.buswayapp.model.ConstantNames;

public class LoginFragment extends Fragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        sharedPreferences = requireContext().getSharedPreferences(ConstantNames.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        EditText email = view.findViewById(R.id.emailEditText);
        EditText password = view.findViewById(R.id.passwordEditText);

//        // Retrieve
//        String typeOfUser = sharedPreferences.getString(ConstantNames.TYPE_OF_USER, ConstantNames.WORKER);

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
                if (email.getText().toString().contains(ConstantNames.ELSEWEDY_DOMAIN)){
                    // supervisor
                    if (email.getText().toString().equals("eb@elsewedy.com") && password.getText().toString().equals("123")){
                        // save to sharedPreference
                        editor.putString(ConstantNames.TYPE_OF_USER, ConstantNames.SUPERVISOR);
                        editor.putString(ConstantNames.EMAIL, email.getText().toString());
                        editor.putString(ConstantNames.PASSWORD, password.getText().toString());

                        NavHostFragment.findNavController(LoginFragment.this)
                                .navigate(R.id.processFragment, null, options);
                    }else{
                        Toast.makeText(getContext(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // worker
                    if (email.getText().toString().equals("worker") && password.getText().toString().equals("123")){
                        // save to sharedPreference
                        editor.putString(ConstantNames.TYPE_OF_USER, ConstantNames.WORKER);
                        editor.putString(ConstantNames.EMAIL, email.getText().toString());
                        editor.putString(ConstantNames.PASSWORD, password.getText().toString());

                        NavHostFragment.findNavController(LoginFragment.this)
                                .navigate(R.id.processFragment, null, options);
                    }
                }
                editor.apply();
            }
        });


        return view;
    }
}