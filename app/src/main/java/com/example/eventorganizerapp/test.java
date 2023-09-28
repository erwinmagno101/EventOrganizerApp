package com.example.eventorganizerapp;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class test extends Fragment {

    View view;
    public static String ID;
    ImageView imgtest;
    UserIMGDataBase DB;
    Bitmap imageDB;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_test, container, false);

        DB = new UserIMGDataBase(getContext());
        imgtest = view.findViewById(R.id.imgtest);

        imageDB = DB.getImage(ID);
        imgtest.setImageBitmap(imageDB);



        return view;
    }
}