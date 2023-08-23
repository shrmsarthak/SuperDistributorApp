package com.app.superdistributor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.superdistributor.sr.dealerorders.DealerOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TechnicianHomeActivity extends AppCompatActivity {

    String technicianName;
    Button totalPendencyBtn, closeComplaintBtn, rescheduleVisitBtn, indentSpBtn, reportsBtn;
    int c = 0;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_home);
        database = FirebaseDatabase.getInstance().getReference();
        totalPendencyBtn = findViewById(R.id.totalpendecybtn);
        database.child("Dealers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    for(DataSnapshot dataSnapshot1: dataSnapshot.child("Orders").getChildren())
                    {
                        Log.d("Dataset ", dataSnapshot1.toString());
                        c++;
                        Log.d("count", Integer.toString(c));
                    }
                }
                totalPendencyBtn.setText("Total Pendency - "+ Integer.toString(c));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setTitle("Technician Dashboard");
        }
        closeComplaintBtn = findViewById(R.id.closecomplaint);
        rescheduleVisitBtn = findViewById(R.id.reschedulevisit);
        indentSpBtn = findViewById(R.id.indentforsp);
        reportsBtn = findViewById(R.id.technicianreportbtn);
        technicianName = getIntent().getStringExtra("Username");

        totalPendencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechnicianHomeActivity.this, TechnicianTotalPendencyActivity.class);
                intent.putExtra("TechnicianUsername",technicianName);
                if (c != 0) {
                    startActivity(intent);
                }
                else {
                    Toast.makeText(TechnicianHomeActivity.this, "Nothing Pending", Toast.LENGTH_SHORT).show();
                }
            }
        });

        closeComplaintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechnicianHomeActivity.this, TechnicianHandleComplaintsActivity.class);
                intent.putExtra("TechnicianUsername",technicianName);
                intent.putExtra("History", false);
                startActivity(intent);
            }
        });

        rescheduleVisitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechnicianHomeActivity.this, TechnicianHomeActivity.class);
                intent.putExtra("TechnicianUsername",technicianName);
                startActivity(intent);
            }
        });

        indentSpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechnicianHomeActivity.this, TechnicianSparesRequestActivity.class);
                intent.putExtra("TechnicianUsername",technicianName);
                startActivity(intent);
            }
        });

        reportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TechnicianHomeActivity.this, TechnicianHandleComplaintsActivity.class);
                intent.putExtra("TechnicianUsername",technicianName);
                intent.putExtra("History", true);
                startActivity(intent);
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.srmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_alert)
        {
            Toast.makeText(this, "New Alerts", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}