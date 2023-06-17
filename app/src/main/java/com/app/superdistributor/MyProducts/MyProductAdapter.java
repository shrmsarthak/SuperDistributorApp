package com.app.superdistributor.MyProducts;

import android.content.Context;
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

import com.app.superdistributor.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MyViewHolder> {

    Context context;
    Map<String,String> selectedProduct=new HashMap<>();
    Map<String,Map<String,String>> productdetails=new HashMap<>();

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
        holder.productCategory.setText(products.getProductCategory());
        holder.productPrice.setText("Price : \u20B9 "+products.getPrice());

        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(context, CheckoutActivity.class);
                //context.startActivity(i);

                SharedPreferences sh1 = context.getSharedPreferences("MySharedPref", context.MODE_APPEND);


                if(sh1.getString("productdetails","").equals(""))
                {
                    selectedProduct.clear();
                    selectedProduct.put("ProductName",products.getName());
                    selectedProduct.put("ProductPrice",products.getPrice());
                    selectedProduct.put("ProductID",products.getProductID());


                    productdetails.put(products.getProductID(), selectedProduct);

                    SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref",context.MODE_APPEND);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();

                    Gson gson = new Gson();
                    String json = gson.toJson(productdetails);

                    myEdit.putString("productdetails",json);
                    myEdit.commit();

                    //Toast.makeText(context, productdetails.toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SharedPreferences sh = context.getSharedPreferences("MySharedPref", context.MODE_APPEND);
                    String savedproducts = sh.getString("productdetails", "");

                    Gson gson = new Gson();
                    Map<String,Map<String,String>> productdetails = gson.fromJson(savedproducts, Map.class);

                    selectedProduct.clear();
                    selectedProduct.put("ProductName",products.getName());
                    selectedProduct.put("ProductPrice",products.getPrice());
                    selectedProduct.put("ProductID",products.getProductID());

                    productdetails.put(products.getProductID(), selectedProduct);


                    SharedPreferences.Editor myEdit = sh.edit();
                    Gson gson2 = new Gson();
                    String json = gson2.toJson(productdetails);

                    myEdit.putString("productdetails",json);
                    myEdit.commit();
                }

                Toast.makeText(context, "Item Added to Cart..", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView productName, productPrice, productCategory;

        Button addToCartBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.tvProductName);
            productPrice = itemView.findViewById(R.id.tvProductPrice);
            productCategory = itemView.findViewById(R.id.tvProductCategory);
            addToCartBtn = itemView.findViewById(R.id.addtocart);



        }
    }

}
