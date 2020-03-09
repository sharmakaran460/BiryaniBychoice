package com.example.test.OrderCart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.test.Model.CartModal;
import com.example.test.R;
import com.example.test.Sqldirectory.CartLitedb;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.ViewHolder.Cart_layoutAdapter;

import java.util.ArrayList;
import java.util.List;


public class Cart extends AppCompatActivity {
    String food ="";
    ArrayList<CartModal> cartModalArrayList = new ArrayList<>();

    Toolbar toolbar;
    Button placeorder;
    RelativeLayout relativeLayout;
    TextView remove_donation , item_total;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart);
        toolbar = findViewById(R.id.cartToolbar);
        placeorder = findViewById(R.id.placeorder);
        relativeLayout=findViewById(R.id.donation_layout_id);
        remove_donation=findViewById(R.id.remove_donation);
        item_total =findViewById(R.id.items_total);

        remove_donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.setVisibility(View.GONE);
            }
        });


        setSupportActionBar(toolbar);
        //for toolbar setup
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //database inherited
       DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
            //for recyclerview
        RecyclerView recyclerView = findViewById(R.id.cartview);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        System.out.println("Data base heler in cart "+cartModalArrayList);

        recyclerView.setAdapter(new Cart_layoutAdapter((ArrayList<CartModal>) databaseHelper.getdata(),item_total));



        //for place order button
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Cart.this, "place orderbutton clicked", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
