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

import com.example.test.Model.Book;
import com.example.test.Model.FoodModel;
import com.example.test.ViewHolder.VIewAdapter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

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
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment {
     View view;
  private  ArrayList<FoodModel> foodmodel = new ArrayList<>();
    String data;
    ArrayList<Book> book1;
    ProgressDialog progressDialog;

    public Tab1() {
        // Required empty public constructor
    }
    public Tab1(ArrayList<Book> book2)
    {
        this.book1=book2;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
           view =  inflater.inflate(R.layout.fragment_tab1, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.layout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new VIewAdapter(getContext(),foodmodel));

         return view;

    }
    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new  AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("please wait.......");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(true);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
                progressDialog.isShowing();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://171.50.163.72:8081/biryani/food/cat/veg");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream stream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                    data = reader.readLine();
                    JSONArray array = new JSONArray(data);
                    for (int i = 0; i < array.length(); i++) {
                        FoodModel food = new FoodModel();
                        JSONObject object = (JSONObject) array.get(i);
                        food.setFoodName(object.getString("food_name"));
                        food.setFoodCat(object.getString("food_cat"));
                        food.setFoodPrice(object.getInt("food_price"));
                        food.setFoodDes(object.getString("food_desc"));
                        food.setFood_imag_url(object.getString("food_image_blob"));
                        foodmodel.add(i, food);

                }
                    System.out.println("data ye haii.............................."+foodmodel);

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
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                RecyclerView recyclerView = view.findViewById(R.id.layout);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(new VIewAdapter(getContext(),foodmodel));
            }
        }.execute();

    }
}


