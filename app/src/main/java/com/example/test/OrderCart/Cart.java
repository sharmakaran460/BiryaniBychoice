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
import android.widget.Toast;


import com.example.test.Model.CartModal;
import com.example.test.R;
import com.example.test.Sqldirectory.CartLitedb;
import com.example.test.ViewHolder.Cart_layoutAdapter;

import java.util.ArrayList;
import java.util.List;


public class Cart extends AppCompatActivity {
    String food ="";
    ArrayList<CartModal> cartModalArrayList = new ArrayList<>();

    Toolbar toolbar;
    Button placeorder;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart);
        toolbar = findViewById(R.id.cartToolbar);
        placeorder = findViewById(R.id.placeorder);

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
        CartLitedb cartLitedb = new CartLitedb(getApplicationContext());
//for recyclerview
        RecyclerView recyclerView = findViewById(R.id.cartview);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        System.out.println("ye hai data....."+cartModalArrayList);

        recyclerView.setAdapter(new Cart_layoutAdapter((ArrayList<CartModal>) cartLitedb.getdata()) );








        //for place order button
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Cart.this, "place orderbutton clicked", Toast.LENGTH_SHORT).show();
            }
        });



    }


}
