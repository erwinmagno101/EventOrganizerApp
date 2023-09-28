package com.example.eventorganizerapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Clogin extends Fragment implements View.OnClickListener {

    View view;
    Button btnCBack, btnCSignup, btnCLogin;
    CheckBox btnCCheck;
    EditText cUsername, cPassword;
    DBHelper DB;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_clogin, container, false);

        btnCBack = view.findViewById(R.id.btnCBack);
        btnCBack.setOnClickListener(this);
        btnCSignup = view.findViewById(R.id.btnCSignUp);
        btnCSignup.setOnClickListener(this);
        btnCCheck = view.findViewById(R.id.btnCCheck);
        btnCCheck.setOnClickListener(this);
        btnCLogin = view.findViewById(R.id.btnCLogin);
        btnCLogin.setOnClickListener(this);
        cUsername = view.findViewById(R.id.cUsername);
        cPassword = view.findViewById(R.id.cPassword);
        DB = new DBHelper(getContext());

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == btnCBack){
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        if(view == btnCSignup){
            Fragment fragment = new CSignup();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_right_to_left, R.anim.exit_right_to_left);
            fragmentTransaction.replace(R.id.frameLayout,fragment);
            fragmentTransaction.commit();
        }

        if (view == btnCCheck){
            if (btnCCheck.isChecked()){
                cPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
                cPassword.setTypeface(null, Typeface.BOLD);
            }else{
                cPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                cPassword.setTypeface(null, Typeface.BOLD);
            }

        }

        if(view == btnCLogin){
            String username = cUsername.getText().toString();
            String password = cPassword.getText().toString();
            Boolean confrimation = DB.checkCatererLogin(username, password);

            if(confrimation){
                Toast.makeText(getContext(), "Successfully Logged in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), CatererDashboard.class);
                startActivity(intent);
            }else{
                Toast.makeText(getContext(), "Incorrect Credentials", Toast.LENGTH_SHORT).show();
            }

        }


    }
}