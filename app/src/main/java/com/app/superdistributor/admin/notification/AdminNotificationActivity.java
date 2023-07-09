package com.app.superdistributor.admin.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.superdistributor.R;
import com.app.superdistributor.sr.dealerorders.DealerOrder;
import com.app.superdistributor.sr.dealerorders.DealerOrderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminNotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    ProductConfirmationAdapter myAdapter;
    ArrayList<ProductConfirmationModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification);

        recyclerView = findViewById(R.id.notificationRLV);
        database = FirebaseDatabase.getInstance().getReference();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new ProductConfirmationAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        database.child("Admin").child("Notifications").child("ProductConfirmation").child("SRs")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();

                        for(DataSnapshot dataSnapshot: snapshot.getChildren())
                        {
                            ProductConfirmationModel productConfirmationModel = dataSnapshot.getValue(ProductConfirmationModel.class);
                            list.add(productConfirmationModel);
                        }


                        myAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        myAdapter.notifyDataSetChanged();

    }
}