package com.app.superdistributor.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.superdistributor.CustomerAccountDetails;
import com.app.superdistributor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewDealerActivity extends AppCompatActivity {

    private ListView DealerDataLV;

    ArrayList<String> dealersList;
    DatabaseReference database;

    ArrayList<String> dealersArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dealer);

        DealerDataLV = findViewById(R.id.viewDealerlv);
        dealersList = new ArrayList<String>();
        initializeListView();
    }

    private void initializeListView() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dealersList);

        database = FirebaseDatabase.getInstance().getReference();

        dealersArrayList.clear();
        database.child("Dealers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    dealersArrayList.add(snap.getKey());
                }

                for(int i=0; i<dealersArrayList.size(); i++)
                {
                    dealersList.add("Dealer "+String.valueOf(i+1)+" - "+dealersArrayList.get(i)+"@gmail.com");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DealerDataLV.setAdapter(adapter);
    }
}