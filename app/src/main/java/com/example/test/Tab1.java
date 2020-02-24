package com.example.test;


import android.os.Bundle;


import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.test.Model.FoodModel;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.ViewHolder.NewCardAdapter;

import com.example.test.utlity.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment {
        View view;
    static  String   Url ="http://61.247.229.49:8082/biryaniweb/food/";
    final ArrayList<FoodModel> foodlists=new ArrayList<>();
    TextView cart_amout;
    TextView items_total;
    DatabaseHelper data_base;


    public Tab1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
           view =  inflater.inflate(R.layout.fragment_tab1, container, false);
        cart_amout = view.findViewById(R.id.cart_amout);
        items_total=view.findViewById(R.id.items_total);
        data_base=new DatabaseHelper(getActivity());
        String amount =  data_base.get_the_total_amount();
        String quantity = data_base. get_the_total_quantity();
        if(amount!=null)
        {
            cart_amout.setText("₹"+amount);
        }
        if(quantity!=null)
        {
            items_total.setText(""+quantity+" Item");
        }





        RecyclerView recyclerView= view.findViewById(R.id.layout);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        recyclerView.setAdapter(new NewCardAdapter(foodlists,getActivity(),cart_amout,items_total));
        recyclerView.addItemDecoration(new SpacesItemDecoration(8));


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
                    recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
                    recyclerView.setAdapter(new NewCardAdapter(foodlists));
                    recyclerView.addItemDecoration(new SpacesItemDecoration(8));

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

