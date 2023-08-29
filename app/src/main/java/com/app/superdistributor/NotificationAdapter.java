package com.app.superdistributor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>{

    Context context;
    ArrayList<NotificationItemModel> list;
    DatabaseReference databaseReference;
    public NotificationAdapter(Context context, ArrayList<NotificationItemModel> list){
        this.context = context;
        this.list = list;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
    @NonNull
    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationAdapter.MyViewHolder(
                LayoutInflater.from(context).inflate(R.layout.complaint_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, int position) {

        NotificationItemModel notificationItemModel = list.get(position);
        holder.type.setText(notificationItemModel.getNotificationType());
        holder.tag.setText(notificationItemModel.getNotificationTag());
        holder.description.setText(notificationItemModel.getNotificationDesc());
        //TODO : on click dialog
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView type, tag, description;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.dealer_name);
            tag = itemView.findViewById(R.id.tag);
            description = itemView.findViewById(R.id.complaint_desc);
        }
    }
}
