package com.app.superdistributor.sr.payments;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.superdistributor.R;

import java.util.ArrayList;

public class PaymentStatusAdapter extends RecyclerView.Adapter<PaymentStatusAdapter.MyViewHolder> {

    Context context;
    ArrayList<PaymentStatusModel> list;

    public PaymentStatusAdapter(Context context, ArrayList<PaymentStatusModel> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public PaymentStatusAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sr_payment_status_item, parent, false);
        return new PaymentStatusAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentStatusAdapter.MyViewHolder holder, int position) {
        PaymentStatusModel paymentStatusModel = list.get(position);
        holder.roundedTv.setText(paymentStatusModel.getName().substring(0,1).toUpperCase());
        holder.name.setText(paymentStatusModel.getName());
        holder.type.setText(paymentStatusModel.getType());
        holder.amount.setText(paymentStatusModel.getAmount());
        holder.status.setText(paymentStatusModel.getStatus());
        if(paymentStatusModel.getStatus().equals("Approved")){
            holder.status.setBackgroundColor(Color.parseColor("#43A047"));
        }else {
            holder.status.setBackgroundColor(Color.parseColor("#FFB300"));
        }
        //TODO : Add button on click listener
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, type, amount, roundedTv;
        Button status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.paymentNameTv);
            type = itemView.findViewById(R.id.typeTv);
            amount = itemView.findViewById(R.id.amountTv);
            roundedTv = itemView.findViewById(R.id.roundedNameTv);
            status = itemView.findViewById(R.id.statusBtn);
        }
    }
}
