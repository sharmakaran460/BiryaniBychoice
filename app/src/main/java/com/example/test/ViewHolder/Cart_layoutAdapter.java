package com.example.test.ViewHolder;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.test.Model.CartModal;
import com.example.test.R;
import com.example.test.Sqldirectory.CartLitedb;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Cart_layoutAdapter extends RecyclerView.Adapter<Cart_layoutAdapter.CartViewHolder> {
    View view;
 ArrayList<CartModal> cartModalArrayList;



    public Cart_layoutAdapter(ArrayList<CartModal> cartModalArrayList) {
        this.cartModalArrayList = cartModalArrayList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
       view = inflater.inflate(R.layout.cart_layout,parent,false);

        CartViewHolder holder = new CartViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
   final CounterClass[] counterClasses=new CounterClass[cartModalArrayList.size()];
   counterClasses[position]=new CounterClass();
   counterClasses[position].setCount(1);



       holder.itemName.setText(cartModalArrayList.get(position).getCart_item_name());

       holder.itemquantity.setText((String.valueOf(cartModalArrayList.get(position).getQuantity())));

        final CounterClass counterClass = new CounterClass();

        counterClass.setCount(cartModalArrayList.get(position).getQuantity());


        holder.itemPrice.setText(String.valueOf(cartModalArrayList.get(position).getCart_item_price()));

        try {
            holder.itemimg.setImageBitmap(BitmapFactory.decodeByteArray(cartModalArrayList.get(position).getImage(),0,cartModalArrayList.get(position).getImage().length));
        }catch (Exception e){}


        //new DownlordImage(holder.itemimg).execute(cartModalArrayList.get(position).getCart_item_img_url());
        System.out.println("here is cart"+cartModalArrayList);


        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemquantity.setText(String.valueOf(counterClass.mincount()));
              if (counterClass.getCount()<=1){
                    CartLitedb db = new CartLitedb(v.getContext());
                   db.deletebyname(cartModalArrayList.get(position).getCart_item_name());
                   db.close();

                   cartModalArrayList.remove(position);

                   notifyDataSetChanged();


                }

            }
        });

        holder.btn_pluss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /*int x=counterClasses[position].addcount();*/

                holder.itemquantity.setText(String.valueOf(counterClass.addcount()));


            }
        });
    }

    @Override
    public int getItemCount() {
        return cartModalArrayList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        ImageView itemimg;
        TextView itemName ,itemquantity ,itemPrice;
        ImageButton btn_minus,btn_pluss;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemimg = itemView.findViewById(R.id.cart_img);
            itemName = itemView.findViewById(R.id.cart_name);
            itemquantity = itemView.findViewById(R.id.cart_quantity);
            itemPrice = itemView.findViewById(R.id.cart_price);
            btn_minus= itemView.findViewById(R.id.btnminus);
            btn_pluss = itemView.findViewById(R.id.btnplus);
        }
    }

    public class DownlordImage extends AsyncTask<String,Void, Bitmap> {
        ImageView img;

        public DownlordImage(ImageView img) {
            this.img = img;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            System.out.println("i am in  pic");

            Bitmap bitmap =null;
            try {
                String urlLoad= urls[0];
                System.out.println("i am in try pic");
                URL url=new URL(urlLoad);
                InputStream stream=url.openStream();
                bitmap= BitmapFactory.decodeStream(stream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e)
            {
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
