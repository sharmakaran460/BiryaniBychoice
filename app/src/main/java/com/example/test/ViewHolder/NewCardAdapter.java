package com.example.test.ViewHolder;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Model.FoodModel;
import com.example.test.R;
import com.example.test.Sqldirectory.DatabaseHelper;

import java.util.ArrayList;

public class NewCardAdapter extends RecyclerView.Adapter<NewCardAdapter.ViewHolder> {
    View view;
    ArrayList<FoodModel> food_list;
    DatabaseHelper data_base;
    Context context;
    TextView cart_amout,items_total;
    LinearLayout bottom_sheet_layout;

    public NewCardAdapter(ArrayList<FoodModel> food_list, Context context, TextView cart_amout, TextView items_total, LinearLayout bottom_sheet_layout) {
        this.food_list = food_list;
        this.context = context;
        this.cart_amout = cart_amout;
        this.items_total = items_total;
        this.bottom_sheet_layout = bottom_sheet_layout;
    }

    public NewCardAdapter(ArrayList<FoodModel> food_list) {
        this.food_list = food_list;
    }
    public NewCardAdapter(ArrayList<FoodModel> food_list, Context context, TextView cart_amout, TextView items_total)
    {
        this.food_list = food_list;
        this.context = context;
        this.cart_amout = cart_amout;
        this.items_total = items_total;
        data_base = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        view=inflater.inflate(R.layout.new_food_card,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final int[] quintity = {0};
        holder.food_name.setText(food_list.get(position).getFoodName());
        holder.food_price.setText(String.valueOf(food_list.get(position).getFoodPrice()));
        holder.food_desc.setText(food_list.get(position).getFoodDes());
        quintity[0] = food_list.get(position).getQuantity();

        if(food_list.get(position).getFoodCat().equals("veg"))
        {
            holder.cat_icon_image.setImageResource(R.drawable.veg);
        }
        else {
                 holder.cat_icon_image.setImageResource(R.drawable.nonveg);
        }

        holder.add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quintity[0] =  quintity[0]+1;
                holder.counter_text.setText(String.valueOf(quintity[0]));
                long result = data_base.save_cart_value(String.valueOf(food_list.get(position).getFoodid()),  food_list.get(position).getFoodName(),food_list.get(position).getFoodDes(),"image",String.valueOf(food_list.get(position).getFoodPrice()),Integer.toString(quintity[0]));
                Log.e("Result",result+"");
                holder.linearLayout_btn.setVisibility(View.INVISIBLE);
                holder.linearLayout.setVisibility(View.VISIBLE);
                //bottom_sheet_layout.setVisibility(View.VISIBLE);

                if(result>0)
                {
                    String amount =  data_base.get_the_total_amount();
                    String quantity = data_base. get_the_total_quantity();
                    cart_amout.setText("₹"+amount);
                    if(quantity!=null)
                    {
                        items_total.setText(""+quantity+" Item");
                    }
                    Log.e("Result_amount",amount+"");
                }


            }
        });
        holder.add_counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quintity[0]=quintity[0]+1;
                holder.counter_text.setText(String.valueOf(quintity[0]));

                long result = data_base.save_cart_value(String.valueOf(food_list.get(position).getFoodid()),  food_list.get(position).getFoodName(),food_list.get(position).getFoodDes(),"image",String.valueOf(food_list.get(position).getFoodPrice()),String.valueOf(quintity[0]));


                Log.e("Result _Add",quintity[0]+"");

                //     long result =   data_base.save_cart_value(food_list.get(position).getFood_id(),  food_list.get(position).getFoodName(),food_list.get(position).getFoodDes(),"image",String.valueOf(food_list.get(position).getFoodPrice()),Integer.toString(food_list.get(position).getQuantity()));

                if(result>0)
                    if(result>0)
                    {
                        String amount =  data_base.get_the_total_amount();
                        String quantity = data_base. get_the_total_quantity();
                        cart_amout.setText("₹"+amount);
                        if(quantity!=null)
                        {
                            items_total.setText(""+quantity+" Item");
                        }
                        Log.e("Result_amount",amount+"");
                    }
            }
        });
        holder.min_counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quintity[0]>0)
                {
                    quintity[0]=quintity[0]-1;
                    holder.counter_text.setText(String.valueOf(quintity[0]));
                    long result = data_base.save_cart_value(String.valueOf(food_list.get(position).getFoodid()),  food_list.get(position).getFoodName(),food_list.get(position).getFoodDes(),"image",String.valueOf(food_list.get(position).getFoodPrice()),String.valueOf(quintity[0]));

                    if(result>0)
                    {
                        String amount =  data_base.get_the_total_amount();
                        String quantity = data_base. get_the_total_quantity();
                        cart_amout.setText("₹"+amount);
                        if(quantity!=null)
                        {
                            items_total.setText(""+quantity+" Item");
                        }
                        Log.e("Result_amount",amount+"");
                    }
                }
                else
                {
                    holder.add_btn.setVisibility(View.VISIBLE);
                    holder.linearLayout_btn.setVisibility(View.VISIBLE);
                    holder.linearLayout.setVisibility(View.INVISIBLE);
                    //bottom_sheet_layout.setVisibility(View.INVISIBLE);

                }



                Log.e("Result Sub",quintity[0]+"");
            }
        });

        try{
            holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(food_list.get(position).getImage(),0,food_list.get(position).getImage().length));
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return food_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, cat_icon_image;
        TextView food_name, food_price, food_desc, counter_text;
        Button add_btn;
        ImageButton  add_counter, min_counter;
        LinearLayout linearLayout, linearLayout_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_icon_image=itemView.findViewById(R.id.new_c_cat_icon);
            food_desc=itemView.findViewById(R.id.new_c_desc);
            imageView=itemView.findViewById(R.id.new_c_image);
            food_name=itemView.findViewById(R.id.new_c_f_name);
            food_price=itemView.findViewById(R.id.new_c_price);
            add_btn=itemView.findViewById(R.id.new_c_add_btn);
            linearLayout=itemView.findViewById(R.id.new_c_counter_layout);
            add_counter=itemView.findViewById(R.id.new_c_count_plus);
            min_counter=itemView.findViewById(R.id.new_c_count_min);
            counter_text=itemView.findViewById(R.id.new_c_count_num);
            linearLayout_btn= itemView.findViewById(R.id.linear_layout_button);

        }
    }
}
