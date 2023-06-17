package com.app.superdistributor.MyProducts;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.app.superdistributor.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private ArrayList<MyListData> listdata;

    ArrayList<String> productId = new ArrayList<>();

    // RecyclerView recyclerView;
    public MyListAdapter(ArrayList<MyListData> listdata) {
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.cart_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyListData myListData = listdata.get(position);
        holder.NametextView.setText(listdata.get(position).getName());
        holder.PricetextView.setText("\u20B9"+listdata.get(position).getPrice());
        holder.QtyView.setText("Qty : "+listdata.get(position).getQty());
        //holder.imageView.setImageResource(listdata[position].getImgId());
        //holder.imageView.setImageResource(R.drawable.about);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+myListData.getName(), Toast.LENGTH_LONG).show();
            }
        });

        holder.RemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Item Removed"+holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();



                SharedPreferences sh = v.getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                String savedproducts = sh.getString("productdetails", "");

                Gson gson = new Gson();
                Map<String, Map<String,String>> productdetails = gson.fromJson(savedproducts, Map.class);

                for (Iterator<String> it = productdetails.keySet().iterator(); it.hasNext(); ) {
                    productId.add(it.next());
                }
                //Toast.makeText(v.getContext(), ""+productId, Toast.LENGTH_SHORT).show();

                productdetails.remove(productId.get(holder.getAdapterPosition()));
                productId.remove(holder.getAdapterPosition());

                listdata.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

                if(listdata.size()<1)
                {
                    CartViewActivity.EmptyCartView.setVisibility(View.VISIBLE);
                }
                else
                {
                    CartViewActivity.EmptyCartView.setVisibility(View.INVISIBLE);
                }

                SharedPreferences.Editor myEdit = sh.edit();
                Gson gson2 = new Gson();
                String json = gson2.toJson(productdetails);

                myEdit.putString("productdetails",json);
                myEdit.commit();

            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ToolimageView;
        public TextView NametextView, PricetextView, QtyView;
        public RelativeLayout relativeLayout;
        public Button RemoveBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            this.NametextView = (TextView) itemView.findViewById(R.id.nametextView);
            this.PricetextView = (TextView) itemView.findViewById(R.id.pricetextView);
            this.RemoveBtn = (Button) itemView.findViewById(R.id.removebtn);
            this.QtyView = (TextView) itemView.findViewById(R.id.qtyView);

            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayoutcart2);
        }
    }
}