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

public class ViewTechnicianActivity extends AppCompatActivity {

    private ListView TechnicianDataLV;

    ArrayList<String> techniciansList;
    DatabaseReference database;

    ArrayList<String> techniciansArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_technician);

        TechnicianDataLV = findViewById(R.id.viewTechnicianlv);
        techniciansList = new ArrayList<String>();
        initializeListView();
    }

    private void initializeListView() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, techniciansList);

        database = FirebaseDatabase.getInstance().getReference();

        techniciansList.clear();
        database.child("Technicians").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    techniciansArrayList.add(snap.getKey());
                }

                for(int i=0; i<techniciansArrayList.size(); i++)
                {
                    techniciansList.add("Technician "+String.valueOf(i+1)+" - "+techniciansArrayList.get(i)+"@gmail.com");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TechnicianDataLV.setAdapter(adapter);
    }
}