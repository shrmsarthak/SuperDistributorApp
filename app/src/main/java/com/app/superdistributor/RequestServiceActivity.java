package com.app.superdistributor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.superdistributor.RequestService.HandoverDRActivity;
import com.app.superdistributor.RequestService.MaterialPendencyActivity;
import com.app.superdistributor.RequestService.RaiseServiceConcerActivity;
import com.app.superdistributor.RequestService.RegisterComplaintAcitivty;
import com.app.superdistributor.RequestService.ReplaceByDealerActivity;
import com.app.superdistributor.RequestService.ServiceContactDetailActivity;

public class RequestServiceActivity extends AppCompatActivity {

    Button ReplaceByDealerBtn, RegisterComplaintBtn, HandOverBtn, ServiceContactBtn, MaterialPendencyBtn, RaiseConcernBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);

        ReplaceByDealerBtn = findViewById(R.id.replacedbydealerbtn);
        RegisterComplaintBtn = findViewById(R.id.registercomplaints);
        HandOverBtn = findViewById(R.id.handoverbtn);
        ServiceContactBtn = findViewById(R.id.servicecontactbtn);
        MaterialPendencyBtn = findViewById(R.id.materialpendencybtn);
        RaiseConcernBtn= findViewById(R.id.raiseconcernbtn);

        ReplaceByDealerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RequestServiceActivity.this, ReplaceByDealerActivity.class);
                startActivity(i);
            }
        });

        RegisterComplaintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RequestServiceActivity.this, RegisterComplaintAcitivty.class);
                startActivity(i);
            }
        });

        HandOverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RequestServiceActivity.this, HandoverDRActivity.class);
                startActivity(i);
            }
        });

        ServiceContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RequestServiceActivity.this, ServiceContactDetailActivity.class);
                startActivity(i);
            }
        });

        MaterialPendencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RequestServiceActivity.this, MaterialPendencyActivity.class);
                startActivity(i);
            }
        });

        RaiseConcernBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RequestServiceActivity.this, RaiseServiceConcerActivity.class);
                startActivity(i);
            }
        });
    }
}