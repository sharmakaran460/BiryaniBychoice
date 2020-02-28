package com.example.test;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.BAckgrounddata.GetData;
import com.example.test.Model.FoodModel;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.ViewHolder.ComboAdapter;
import com.example.test.ViewHolder.NewCardAdapter;
import com.example.test.utlity.SpacesItemDecoration;

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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    View view;
    //final ArrayList<FoodModel> foodModels=new ArrayList<>();
    ArrayList<FoodModel> combolist = new ArrayList<>();
    TextView cart_amout;
    TextView items_total;
    DatabaseHelper data_base;
    LinearLayout bottomsheet;
    GetData getData=new GetData();
    String eggurl="http://61.247.229.49:8082/biryaniweb/food/cat/eggnon";

    public BlankFragment() {
        // Required empty public constructor
    }

    public BlankFragment(ArrayList<FoodModel> foodModels,TextView cart_amout,TextView items_total,LinearLayout bottomsheet ){
        this.cart_amout =cart_amout;
        this.items_total=items_total;
        this.bottomsheet=bottomsheet;
        this.combolist =foodModels;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_blank, container, false);

        /*cart_amout = view.findViewById(R.id.cart_amout);
        items_total=view.findViewById(R.id.items_total);
        bottomsheet =view.findViewById(R.id.bottom_sheet);*/
        getData=new GetData();


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

/*        //data yaha se aa raha hai
        getData.getalldata(eggurl, getContext(), new GetData.CallBack() {
            @Override
            public void onSuccess(ArrayList<FoodModel> foodModelsAll) {
                RecyclerView recyclerView= view.findViewById(R.id.comborecycler);
                recyclerView.hasFixedSize();
                recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));

                recyclerView.setAdapter(new NewCardAdapter(foodModelsAll,getContext(),cart_amout,items_total,bottomsheet));
            }
        });*/



       // recyclerView.addItemDecoration(new SpacesItemDecoration(8));
        RecyclerView recyclerView= view.findViewById(R.id.comborecycler);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));

        recyclerView.setAdapter(new NewCardAdapter(combolist,getContext(),cart_amout,items_total,bottomsheet));


        return view;
    }
    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// koi jarurat nai hai teri

    }
}
