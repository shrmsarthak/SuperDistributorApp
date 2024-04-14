package com.app.superdistributor.sr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.superdistributor.CheckoutActivity;
import com.app.superdistributor.MyProducts.MyProductAdapter;
import com.app.superdistributor.MyProducts.PlaceOrderActivity;
import com.app.superdistributor.MyProducts.Products;
import com.app.superdistributor.R;
import com.app.superdistributor.sr.dealerorders.DealerOrder;
import com.app.superdistributor.sr.dealerorders.DealerOrderAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DealerIntentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    DealerOrderAdapter myAdapter;
    ArrayList<DealerOrder> list;

    String SRUsername;

    Map<String, Object> dealerProductOrderMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_intent);

        dealerProductOrderMap = new HashMap<>();

        SRUsername = getIntent().getStringExtra("SRUsername");

        recyclerView = findViewById(R.id.dealerorderList);
        database = FirebaseDatabase.getInstance().getReference();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new DealerOrderAdapter(this,list, SRUsername);
        recyclerView.setAdapter(myAdapter);

        database.child("Dealers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    for(DataSnapshot dataSnapshot1: dataSnapshot.child("Orders").getChildren())
                    {
                        DealerOrder dealerOrder = dataSnapshot1.getValue(DealerOrder.class);
                        list.add(dealerOrder);
                    }
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