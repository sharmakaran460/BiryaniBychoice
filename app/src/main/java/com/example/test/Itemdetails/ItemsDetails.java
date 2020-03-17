package com.example.test.Itemdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.BAckgrounddata.GetData;
import com.example.test.Model.FoodModel;
import com.example.test.R;

import java.util.ArrayList;

public class ItemsDetails extends AppCompatActivity {

    Toolbar toolbar;
    ImageView foodImage;
    TextView foodTitle,foodDesc,foodPrice;

    String toolbarTitle;
    String image;
    String title;
    String desc;
    int price;



    byte[] images;

    public  ItemsDetails(){}

    public ItemsDetails(String toolbarTitle,String title,String desc,int price){
    this.toolbarTitle =toolbarTitle;
    //this.image =image;
    this.title=title;
    this.desc =desc;
    this.price=price;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_details);

        toolbar =findViewById(R.id.toolbar);
        foodImage =findViewById(R.id.food_image);
        foodTitle =findViewById(R.id.food_title);
        foodDesc =findViewById(R.id.food_desc);
        foodPrice = findViewById(R.id.food_price);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle=getIntent().getExtras();
        int id= bundle.getInt("foodid");
        String url="http://122.160.81.156:8081/biryaniweb/food/"+id;


        GetData getData = new GetData();
        getData.getsingledata(url, getApplicationContext(), new GetData.CallBackOne() {
            @Override
            public void onSuccess(FoodModel foodModel) {


                toolbar.setTitle(foodModel.getFoodName());
                foodTitle.setText(foodModel.getFoodName());
                foodDesc.setText(foodModel.getFoodDes());
                foodPrice.setText(String.valueOf(foodModel.getFoodPrice()));
                if (foodModel!= null) {
                    try {
                        foodImage.setImageBitmap(BitmapFactory.decodeByteArray(foodModel.getImage(), 0, foodModel.getImage().length));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

