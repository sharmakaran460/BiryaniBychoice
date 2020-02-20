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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.BAckgrounddata.GetData;
import com.example.test.Model.FoodModel;
import com.example.test.ViewHolder.VIewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment {
        View view;
    static  String   Url ="http://61.247.229.49:8082/biryaniweb/food/cat/veg/";
    final ArrayList<FoodModel> foodlists=new ArrayList<>();


    public Tab1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
           view =  inflater.inflate(R.layout.fragment_tab1, container, false);





        RecyclerView recyclerView= view.findViewById(R.id.layout);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new VIewAdapter(getContext().getApplicationContext(),foodlists));


        Toast.makeText(getContext().getApplicationContext(), "on crete view", Toast.LENGTH_SHORT).show();

         return view;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        RequestQueue queue = Volley.newRequestQueue(getContext().getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("in try catch............................");
                    JSONArray array = new JSONArray(response);
                    for (int i =0; i< array.length();i++){
                        FoodModel foodItem = new FoodModel();
                        JSONObject object = array.getJSONObject(i);
                        foodItem.setFoodName(object.getString("food_name"));
                        foodItem.setFoodCat(object.getString("food_cat"));
                        foodItem.setFoodDes(object.getString("food_desc"));
                        foodItem.setFoodPrice(object.getInt("food_price"));
                        foodItem.setFood_imag_url(object.getString("food_image_blob"));
                        foodlists.add(foodItem);

                    }
                    RecyclerView recyclerView= view.findViewById(R.id.layout);
                    recyclerView.hasFixedSize();
                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    recyclerView.setAdapter(new VIewAdapter(getContext().getApplicationContext(),foodlists));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);




    }



}

