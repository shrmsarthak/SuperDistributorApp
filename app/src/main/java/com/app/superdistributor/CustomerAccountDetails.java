package com.app.superdistributor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.superdistributor.MyProducts.MyProductAdapter;
import com.app.superdistributor.MyProducts.Products;
import com.app.superdistributor.models.ExpenseModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerAccountDetails extends AppCompatActivity {

    String customerName;
    TextView CurrentBalanceTv;
    RecyclerView recyclerView;
    DatabaseReference database;
    MyExpenseAdapter myAdapter;
    ArrayList<ExpenseModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_account_details);

        Intent intent = getIntent();
        customerName = intent.getStringExtra("customername");

        CurrentBalanceTv = findViewById(R.id.currentbalancetxt);

        recyclerView = findViewById(R.id.expenselist);
        database = FirebaseDatabase.getInstance().getReference();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyExpenseAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        database.child("Customers").child(customerName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    if(!(dataSnapshot.getValue() instanceof String))
                    {
                        ExpenseModel expenseModel = dataSnapshot.getValue(ExpenseModel.class);
                        list.add(expenseModel);
                    }

                }
                myAdapter.notifyDataSetChanged();

                //Toast.makeText(CustomerAccountDetails.this, "list"+snapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.child("Customers").child(customerName).child("CurrentBalance").
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        CurrentBalanceTv.setText("Current Balance : "+snapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}