package com.app.superdistributor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.superdistributor.admin.paymenthistory.AmountOverviewModel;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    Context context;
    ArrayList<MessageModel> list;

    public MessagesAdapter(Context context, ArrayList<MessageModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MessagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_item,parent,false);
        return new MessagesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.MyViewHolder holder, int position) {
        MessageModel messageModel = list.get(position);
        if (list.isEmpty()) Toast.makeText(context, messageModel.toString(), Toast.LENGTH_SHORT).show();
        holder.senderNameTv.setText(messageModel.getSender());
        holder.messageBodyTv.setText(messageModel.getMessage());
        holder.markAsReadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView senderNameTv, messageBodyTv;
        Button markAsReadBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            senderNameTv = itemView.findViewById(R.id.sender);
            messageBodyTv = itemView.findViewById(R.id.message_body);
            markAsReadBtn = itemView.findViewById(R.id.read_btn);
        }
    }
}
