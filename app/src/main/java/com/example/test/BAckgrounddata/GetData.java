package com.example.test.BAckgrounddata;

import android.content.Context;
import android.content.pm.PackageManager;

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
import java.util.List;

public class GetData {

   public ArrayList<FoodModel> foodlistAll;

  public GetData(){
  }


    public void setFoodModels(ArrayList<FoodModel> foodlist) {
        this.foodlistAll=foodlist;
    }

    public ArrayList<FoodModel> getFoodModels() {

          return foodlistAll;
    }



    public void getalldata(String url, Context context, final CallBack callBack ){
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        final  ArrayList<FoodModel> foodlists=new ArrayList<>();
        final  ArrayList<FoodModel> foodlistsveg=new ArrayList<>();
        final  ArrayList<FoodModel> foodlistsnonveg=new ArrayList<>();
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



                        if(object.getString("food_cat").equals("veg")){
                            FoodModel foodItemveg = new FoodModel();
                            foodItemveg.setFoodName(object.getString("food_name"));
                            foodItemveg.setFoodCat(object.getString("food_cat"));
                            foodItemveg.setFoodDes(object.getString("food_desc"));
                            foodItemveg.setFoodPrice(object.getInt("food_price"));
                            foodItemveg.setFood_imag_url(object.getString("food_image_blob"));
                            foodlistsveg.add(foodItemveg);
                        }
                        if (object.getString("food_cat").equals("nonveg") || object.getString("food_cat").equals("egg")){
                            FoodModel foodItemeggnon = new FoodModel();
                            foodItemeggnon.setFoodName(object.getString("food_name"));
                            foodItemeggnon.setFoodCat(object.getString("food_cat"));
                            foodItemeggnon.setFoodDes(object.getString("food_desc"));
                            foodItemeggnon.setFoodPrice(object.getInt("food_price"));
                            foodItemeggnon.setFood_imag_url(object.getString("food_image_blob"));
                            foodlistsnonveg.add(foodItemeggnon);
                        }


                    }


                    callBack.onSuccess(foodlists,foodlistsveg,foodlistsnonveg);
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

 // public void getAlldata()
  public interface CallBack{
      void onSuccess(ArrayList<FoodModel> foodModelsAll,ArrayList<FoodModel> foodModelsveg,ArrayList<FoodModel> foodModelsegnon);
  }

}
