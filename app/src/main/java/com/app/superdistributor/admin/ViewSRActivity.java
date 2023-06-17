package com.app.superdistributor.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.superdistributor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewSRActivity extends AppCompatActivity {

    private ListView SRDataLV;

    ArrayList<String> srsList;
    DatabaseReference database;

    ArrayList<String> srsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sractivity);

        SRDataLV = findViewById(R.id.viewSRlv);
        srsList = new ArrayList<String>();
        initializeListView();
    }

    private void initializeListView() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, srsList);

        database = FirebaseDatabase.getInstance().getReference();

        srsList.clear();
        database.child("SRs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    srsArrayList.add(snap.getKey());
                }

                for(int i=0; i<srsArrayList.size(); i++)
                {
                    srsList.add("SR "+String.valueOf(i+1)+" - "+srsArrayList.get(i)+"@gmail.com");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SRDataLV.setAdapter(adapter);
    }
}