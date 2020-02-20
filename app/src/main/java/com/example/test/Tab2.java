package com.example.test;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Model.Book;
import com.example.test.Model.FoodModel;
import com.example.test.Model.NonVegBiryani;
import com.example.test.ViewHolder.BiryaniAdapter;
import com.example.test.ViewHolder.ComboAdapter;
import com.example.test.ViewHolder.VIewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2 extends Fragment {
     View view;
    ArrayList<NonVegBiryani> biryaniList = new ArrayList<>();
    final ArrayList<FoodModel> foodModels=new ArrayList<>();

    public Tab2() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
   view = inflater.inflate(R.layout.fragment_tab2,container,false);
        RecyclerView recyclerView= view.findViewById(R.id.biryanirecycler);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new BiryaniAdapter(foodModels));


   return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RequestQueue requestQueue= Volley.newRequestQueue(getContext().getApplicationContext());

        String nonveg="http://61.247.229.49:8082/biryaniweb/food/cat/nonveg";


        StringRequest request = new StringRequest(Request.Method.GET, nonveg,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array =new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                FoodModel foodItem = new FoodModel();
                                JSONObject object = array.getJSONObject(i);
                                foodItem.setFoodName(object.getString("food_name"));
                                foodItem.setFoodCat(object.getString("food_cat"));
                                foodItem.setFoodDes(object.getString("food_desc"));
                                foodItem.setFoodPrice(object.getInt("food_price"));
                                foodItem.setFood_imag_url(object.getString("food_image_blob"));
                                foodModels.add(i,foodItem);
                            }
                            System.out.println("here your fooditemsssssssssssssssssssssssssssssss"+foodModels);
                            RecyclerView recyclerView= view.findViewById(R.id.biryanirecycler);
                            recyclerView.hasFixedSize();
                            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            recyclerView.setAdapter(new BiryaniAdapter(foodModels));

                        }catch (Exception e)
                        {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

    }
}
