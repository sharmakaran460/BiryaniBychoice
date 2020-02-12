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

import com.example.test.Model.Book;
import com.example.test.Model.NonVegBiryani;
import com.example.test.ViewHolder.BiryaniAdapter;
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




    public Tab2() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
   view = inflater.inflate(R.layout.fragment_tab2,container,false);


        RecyclerView recyclerView = view.findViewById(R.id.biryanirecycler);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerView.setAdapter(new BiryaniAdapter(biryaniList));

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
                    URL url = new URL("http://171.50.163.72:8081/biryani/food/cat/nonveg");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream stream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                    data = reader.readLine();
                    JSONArray array = new JSONArray(data);
                    for (int i = 0; i < array.length(); i++) {
                        NonVegBiryani biryani = new NonVegBiryani();
                        JSONObject object = (JSONObject) array.get(i);
                        biryani.setDish_name(object.getString("food_name"));
                        biryani.setDesc(object.getString("food_cat"));
                        biryani.setPrice(object.getInt("food_price"));
                        biryani.setImage_url(object.getString("food_image_blob"));
                        biryaniList.add(i, biryani);
                        biryani.setBiryanilists(biryaniList);

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
                RecyclerView recyclerView = view.findViewById(R.id.biryanirecycler);
                recyclerView.hasFixedSize();
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

                recyclerView.setAdapter(new BiryaniAdapter(biryaniList));


            }
        }.execute();

    }
}
