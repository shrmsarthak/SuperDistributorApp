package com.app.superdistributor.MyProducts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.superdistributor.CheckoutActivity;
import com.app.superdistributor.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MyViewHolder> {

    Context context;
    Map<String,String> selectedProduct=new HashMap<>();
    ArrayList<Products> list;


    public MyProductAdapter(Context context, ArrayList<Products> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.prodcut_item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Products products = list.get(position);
        holder.productName.setText(products.getName());
        holder.AddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedProduct.put(products.getName(), holder.Qty.getText().toString());
                SharedPreferences sharedPreferences = context.getSharedPreferences("shared_prefs",context.MODE_PRIVATE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView productName;
        Button AddProductBtn;

        TextInputEditText Qty;
        
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.tvProductName);
            AddProductBtn = itemView.findViewById(R.id.addProduct);
            Qty = itemView.findViewById(R.id.qty);

        }
    }

}
