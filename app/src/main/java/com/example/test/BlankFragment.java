package com.example.test;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.Model.FoodModel;
import com.example.test.ViewHolder.ComboAdapter;

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
public class BlankFragment extends Fragment {

    View view;
    ArrayList<FoodModel> combolist = new ArrayList<>();


    public BlankFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_blank, container, false);

        RecyclerView recyclerView= view.findViewById(R.id.comborecycler);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new ComboAdapter(combolist));


        return view;
    }
    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AsyncTask<Void, Void, Void>() {
            String data;
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://171.50.163.72:8081/biryani/food/cat/egg");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream stream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                    data  = reader.readLine();
                    JSONArray array = new JSONArray(data);
                    for (int i=0;i<array.length();i++){
                        FoodModel foodModel = new FoodModel();
                        JSONObject object = (JSONObject) array.get(i);
                        foodModel.setFoodName(object.getString("food_name"));
                        foodModel.setFoodCat(object.getString("food_cat"));
                        foodModel.setFoodPrice(object.getInt("food_price"));
                        foodModel.setFood_imag_url(object.getString("food_image_blob"));
                        combolist.add(foodModel);

                }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                RecyclerView recyclerView= view.findViewById(R.id.comborecycler);
                recyclerView.hasFixedSize();
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(new ComboAdapter(combolist));

            }
        }.execute();
    }
}
