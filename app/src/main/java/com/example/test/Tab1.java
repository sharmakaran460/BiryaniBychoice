package com.example.test;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.test.BAckgrounddata.GetData;
import com.example.test.Model.FoodModel;
import com.example.test.ViewHolder.VIewAdapter;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment {
        View view;
    static  String Url ="";
  static ArrayList<FoodModel> foodmodel = new ArrayList<>();

  Context c;
    public Tab1() {
        // Required empty public constructor
    }
   private GetData getData = new GetData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
           view =  inflater.inflate(R.layout.fragment_tab1, container, false);



           getData.getalldata(Url, getContext().getApplicationContext());

        RecyclerView recyclerView= view.findViewById(R.id.layout);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new VIewAdapter(getContext().getApplicationContext(),getData.getFoodModels()));


        Toast.makeText(getContext().getApplicationContext(), "on crete view", Toast.LENGTH_SHORT).show();

         return view;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Url ="http://61.247.229.49:8082/biryani/food/cat/veg/";

       getData.getalldata(Url,getActivity());

        foodmodel=getData.getFoodModels();
        System.out.println("ye on crerate me food moel hai...................................................."+foodmodel);



    }



}

