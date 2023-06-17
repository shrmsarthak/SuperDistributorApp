package com.app.superdistributor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.superdistributor.MyProducts.MyProductAdapter;
import com.app.superdistributor.MyProducts.Products;
import com.app.superdistributor.models.ExpenseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyExpenseAdapter extends RecyclerView.Adapter<MyExpenseAdapter.MyViewHolder>{

    Context context;
    ArrayList<ExpenseModel> list;

    public MyExpenseAdapter(Context context, ArrayList<ExpenseModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyExpenseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.expense_item,parent,false);
        return  new MyExpenseAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyExpenseAdapter.MyViewHolder holder, int position) {
        ExpenseModel expenseModel = list.get(position);
        holder.CreditTv.setText(expenseModel.getCredit());
        holder.DateTv.setText(expenseModel.getDate());
        holder.DebitTv.setText(expenseModel.getDebit());
        holder.DocNoTv.setText(expenseModel.getDocNo());
        holder.NameTv.setText(expenseModel.getName());
        holder.NoteTv.setText(expenseModel.getNote());
        holder.PaticularTv.setText(expenseModel.getParticular());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView CreditTv, DateTv, DebitTv, DocNoTv, NameTv, NoteTv, PaticularTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            CreditTv = itemView.findViewById(R.id.credit);
            DateTv = itemView.findViewById(R.id.date);
            DebitTv = itemView.findViewById(R.id.debit);
            DocNoTv = itemView.findViewById(R.id.docno);
            NameTv = itemView.findViewById(R.id.customername);
            NoteTv = itemView.findViewById(R.id.note);
            PaticularTv = itemView.findViewById(R.id.particular);
        }
    }
}
