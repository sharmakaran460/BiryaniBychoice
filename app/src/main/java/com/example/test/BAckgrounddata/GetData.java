package com.example.test.BAckgrounddata;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Model.FoodModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetData {
  ArrayList<FoodModel> foodlist=new ArrayList<>();

    public void setFoodModels(ArrayList<FoodModel> foodlist) {
        this.foodlist=foodlist;
    }

    public ArrayList<FoodModel> getFoodModels() {
        return foodlist;
    }



    public  void getalldata(String url,Context context){
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        final ArrayList<FoodModel> foodlists=new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                        foodItem.setFoodModelslist(foodlists);
                    }
                    System.out.println("data here in getdata classsss"+foodlists);
                    setFoodModels(foodlists);
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
