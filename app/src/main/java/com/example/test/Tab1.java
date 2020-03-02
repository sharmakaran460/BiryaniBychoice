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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.BAckgrounddata.GetData;
import com.example.test.Model.FoodModel;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.ViewHolder.NewCardAdapter;
import com.example.test.ViewHolder.VIewAdapter;
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
    ArrayList<FoodModel> foodlists=new ArrayList<>();
    TextView cart_amout;
    TextView items_total;
    DatabaseHelper data_base;
    LinearLayout bottom_sheet_layout;
    GetData getData=new GetData();




    public Tab1() {
        // Required empty public constructor
    }
    public  Tab1(ArrayList<FoodModel> foodModels,TextView cart_amout,TextView items_total,LinearLayout bottom_sheet_layout){
        this.cart_amout =cart_amout;
        this.items_total=items_total;
        this.bottom_sheet_layout =bottom_sheet_layout;
        this.foodlists = foodModels;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
           view =  inflater.inflate(R.layout.fragment_tab1, container, false);
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


/*
        getData.getalldata(Url, getContext(), new GetData.CallBack() {
            @Override
            public void onSuccess(ArrayList<FoodModel> foodModelsAll) {
                RecyclerView recyclerView= view.findViewById(R.id.layout);
                recyclerView.hasFixedSize();
                recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
                recyclerView.setAdapter(new NewCardAdapter(foodModelsAll,getContext(),cart_amout,items_total));
            }
        });
*/
        RecyclerView recyclerView= view.findViewById(R.id.layout);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        recyclerView.setAdapter(new NewCardAdapter(foodlists,getContext(),cart_amout,items_total,bottom_sheet_layout));



         return view;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // no need



    }



}

