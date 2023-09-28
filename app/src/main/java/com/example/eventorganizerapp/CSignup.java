package com.example.eventorganizerapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CSignup extends Fragment implements View.OnClickListener {

    View view;
    Button btnAddPic, btnCSign, btnCLog, btnCBack2;
    ImageView imgCaterer;
    ActivityResultLauncher<Intent> resultLauncher;
    EditText cSignUsername, cFirstname, cLastname, cBusinessname, cAddress, cSignPassword, cInstagram, cFacebook, cContact, cBio;
    Bitmap userBitmap;
    UserIMGDataBase DB;
    DBHelper DB2;
    Boolean empty = true;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_c_signup, container, false);

        DB = new UserIMGDataBase(getContext());
        DB2 = new DBHelper(getContext());
        btnCSign = view.findViewById(R.id.btnCSign);
        btnCSign.setOnClickListener(this);
        btnAddPic = view.findViewById(R.id.btnAddPic);
        btnAddPic.setOnClickListener(this);
        btnCLog = view.findViewById(R.id.btnCLog);
        btnCLog.setOnClickListener(this);
        btnCBack2 = view.findViewById(R.id.btnCBack2);
        btnCBack2.setOnClickListener(this);
        cFirstname = view.findViewById(R.id.cFirstname);
        cLastname = view.findViewById(R.id.cLastname);
        cBusinessname = view.findViewById(R.id.cBusinessname);
        cAddress = view.findViewById(R.id.cAddress);
        cSignPassword = view.findViewById(R.id.cSignPassword);
        cInstagram = view.findViewById(R.id.cInstagram);
        cFacebook = view.findViewById(R.id.cFacebook);
        cContact = view.findViewById(R.id.cContact);
        cBio = view.findViewById(R.id.cBio);
        imgCaterer = view.findViewById(R.id.imgCaterer);

        cSignUsername = view.findViewById(R.id.cSignUsername);


         resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent intent = result.getData();
                        if(intent != null){
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), intent.getData());
                                imgCaterer.setImageBitmap(bitmap);
                                userBitmap = bitmap;
                                empty = false;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );



        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == btnAddPic){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            resultLauncher.launch(intent);
        }

        if(view == btnCSign){
            String username = cSignUsername.getText().toString();
            String firstname = cFirstname.getText().toString();
            String lastname = cLastname.getText().toString();
            String businessname = cBusinessname.getText().toString();
            String address = cAddress.getText().toString();
            String password = cSignPassword.getText().toString();
            String insta = cInstagram.getText().toString();
            String fb = cFacebook.getText().toString();
            String contact = cContact.getText().toString();
            String bio = cBio.getText().toString();

            if(TextUtils.isEmpty(username) || TextUtils.isEmpty(firstname) || TextUtils.isEmpty(lastname) || TextUtils.isEmpty(businessname) || TextUtils.isEmpty(address) || TextUtils.isEmpty(password) || TextUtils.isEmpty(insta) || TextUtils.isEmpty(fb) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(bio)){
                Toast.makeText(getContext(), "All fields Required!", Toast.LENGTH_SHORT).show();
            }else{
                if(empty){
                    Toast.makeText(getContext(), "Business Photo Required", Toast.LENGTH_SHORT).show();
                }else{
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    userBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                    byte[] img = byteArray.toByteArray();

                    boolean insert = DB.insertData(username, img);
                    if(insert == true){
                        Boolean checkUsername = DB2.checkCatererUsername(username);
                        if(checkUsername){
                            Toast.makeText(getContext(), "Username Already Taken", Toast.LENGTH_SHORT).show();
                        }else{
                            Boolean insertion = DB2.insertCatererData(username, password, firstname, lastname, businessname, address, insta, fb, contact, bio);
                            if(insertion){
                                Toast.makeText(getContext(), "Caterer Saved!", Toast.LENGTH_SHORT).show();
                                Fragment fragment = new Clogin();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                                fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                                fragmentTransaction.replace(R.id.frameLayout,fragment);
                                fragmentTransaction.commit();
                            }else{
                                Toast.makeText(getContext(), "Failed to save Caterer", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }else{
                        Toast.makeText(getContext(), "Failed To Save Image!", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        }

        if(view == btnCBack2 || view == btnCLog){
            Fragment fragment = new Clogin();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
            fragmentTransaction.replace(R.id.frameLayout,fragment);
            fragmentTransaction.commit();
        }
    }
}