package com.example.test.ViewHolder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Model.FoodModel;
import com.example.test.R;
import com.example.test.Sqldirectory.CartLitedb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class VIewAdapter extends RecyclerView.Adapter<VIewAdapter.ViewHolder> {
   public Intent i;
Context c;
     View view;
    ArrayList<FoodModel> food;
    Dialog mdaialogue;
    TextView dial_name,dial_author,dial_price;


    public VIewAdapter(Context c, ArrayList<FoodModel> foodModel)
    {
        this.c =c;
       food = foodModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType ) {
        final LayoutInflater inflater = LayoutInflater.from(c);
        view = inflater.inflate(R.layout.cards,parent,false);
        final ViewHolder holder = new ViewHolder(view);

mdaialogue = new Dialog(c);
mdaialogue.setContentView(R.layout.cart_dialogue);
dial_name = mdaialogue.findViewById(R.id.dialogue_Name);
dial_author = mdaialogue.findViewById(R.id.dialogue_Author);
dial_price = mdaialogue.findViewById(R.id.dialogue_price);
        return  holder;

    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        
        holder.bookName.setText(food.get(position).getFoodName());
        holder.bookAuthor.setText(food.get(position).getFoodCat());
        holder.bookPrice.setText(String.valueOf(food.get(position).getFoodPrice()));
        holder.desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(c).create();
                dialog.setTitle("Food Description");
                dialog.setMessage(food.get(position).getFoodDes());
                dialog.show();
            }
        });
        System.out.println("ye hai desc mai"+ food.get(position).getFoodDes());

try{
    holder.bookBackground.setImageBitmap(BitmapFactory.decodeByteArray(food.get(position).getImage(),0,food.get(position).getImage().length));
}catch (Exception e)
{
    e.printStackTrace();
}

        //new DownlordImage(holder.bookBackground).execute(food.get(position).getFood_imag_url());

        final CartLitedb cartLitedb= new CartLitedb(view.getContext());

        holder.additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.additem.setEnabled(false);
                holder.additem.setText("Added");

                System.out.println("ye data hai food url mai"+ food.get(position).getFood_imag_url());

              cartLitedb.insertdata(food.get(position).getFoodName(),
                        food.get(position).getFoodPrice(),
                        1,null,food.get(position).getFood_imag_url());



            }
        });

    }

    @Override
    public int getItemCount() {
        return food.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

       final TextView bookName,bookAuthor,bookPrice;
        ImageView bookBackground;
        Button additem,desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.bookTitle);
            bookAuthor = itemView.findViewById(R.id.bookAuthor);
            bookPrice = itemView.findViewById(R.id.bookPrice);
            bookBackground =itemView.findViewById(R.id.bookbackground);
            additem =itemView.findViewById(R.id.additem);
            desc = itemView.findViewById(R.id.bookDesc);

        }
    }
    public class DownlordImage extends AsyncTask<String,Void, Bitmap>{
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
