package com.example.test.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Model.NonVegBiryani;
import com.example.test.OrderCart.Cart;
import com.example.test.R;
import com.example.test.Sqldirectory.CartLitedb;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BiryaniAdapter extends RecyclerView.Adapter<BiryaniAdapter.ViewHolder> {
ArrayList<NonVegBiryani> biryani;
    View view;

    public BiryaniAdapter(ArrayList<NonVegBiryani> biryaniList) {

        biryani = biryaniList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      view = inflater.inflate(R.layout.biryanicards,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

         holder.dishName.setText(biryani.get(position).getDish_name());
        holder.description.setText(biryani.get(position).getDesc());
        holder.price.setText(String.valueOf(biryani.get(position).getPrice()));

        holder.dishimage.setImageBitmap(BitmapFactory.decodeByteArray(biryani.get(position).getImage(),0,biryani.get(position).getImage().length));
        //new DownlordImage(holder.dishimage).execute(biryani.get(position).getImage_url());

        final CartLitedb  cartLitedb = new CartLitedb(view.getContext());

holder.additem.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        holder.additem.setEnabled(false);
        holder.additem.setText("Added");

        cartLitedb.insertdata(biryani.get(position).getDish_name(),
                biryani.get(position).getPrice()
                ,1,null,biryani.get(position).getImage_url());


            }
});

    }

    @Override
    public int getItemCount() {
       return biryani.size();
//    return 0;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
         TextView dishName,description,price;
         ImageView dishimage;
         Button additem;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
         dishName = itemView.findViewById(R.id.dishname);
        dishimage =itemView.findViewById(R.id.dishImage);
        description =itemView.findViewById(R.id.description);
        price =itemView.findViewById(R.id.price);
        additem = itemView.findViewById(R.id.addbtn);

    }

    }

    public class DownlordImage extends AsyncTask<String,Void, Bitmap> {
        ImageView img;

        public DownlordImage(ImageView img) {
            this.img = img;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlLoad= urls[0];
            Bitmap bitmap =null;
            try {
                URL url=new URL(urlLoad);
                InputStream stream=url.openStream();
                bitmap= BitmapFactory.decodeStream(stream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            img.setImageBitmap(bitmap);
        }
    }
}
