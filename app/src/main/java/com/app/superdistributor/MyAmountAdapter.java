package com.app.superdistributor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.superdistributor.models.AmountModel;

import java.util.ArrayList;

public class MyAmountAdapter extends RecyclerView.Adapter<MyAmountAdapter.MyViewHolder>{

    Context context;
    ArrayList<AmountModel> list;

    public MyAmountAdapter(Context context, ArrayList<AmountModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAmountAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.amount_item,parent,false);
        return  new MyAmountAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAmountAdapter.MyViewHolder holder, int position) {
        AmountModel amountModel = list.get(position);
        holder.AmountTv.setText(amountModel.getAmount());
        holder.DateTv.setText(amountModel.getDate());
        holder.DocNoTv.setText(amountModel.getDocNo());
        holder.NameTv.setText(amountModel.getName());
        holder.NoteTv.setText(amountModel.getNote());
        holder.PaticularTv.setText(amountModel.getParticular());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView DateTv, AmountTv, DocNoTv, NameTv, NoteTv, PaticularTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            AmountTv = itemView.findViewById(R.id.amount);
            DateTv = itemView.findViewById(R.id.date);
            DocNoTv = itemView.findViewById(R.id.docno);
            NameTv = itemView.findViewById(R.id.customername);
            NoteTv = itemView.findViewById(R.id.note);
            PaticularTv = itemView.findViewById(R.id.particular);
        }
    }
}
