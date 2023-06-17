package com.app.superdistributor.MyProducts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.superdistributor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlaceOrderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyProductAdapter myAdapter;
    ArrayList<Products> list;

    ImageView MyCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        recyclerView = findViewById(R.id.productList);
        database = FirebaseDatabase.getInstance().getReference();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyCart = findViewById(R.id.mycart);

        list = new ArrayList<>();
        myAdapter = new MyProductAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        MyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i  = new Intent(PlaceOrderActivity.this, CartViewActivity.class);
                startActivity(i);
                //Toast.makeText(BuyProductActivity.this, productdetails.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        database.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){


                    Products products = dataSnapshot.getValue(Products.class);

                    // Toast.makeText(BuyProductActivity.this, name.toString(), Toast.LENGTH_SHORT).show();
                    list.add(products);


                }
                myAdapter.notifyDataSetChanged();

                //Toast.makeText(BuyProductActivity.this, list.get(0).getProductName().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}