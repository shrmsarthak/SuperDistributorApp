package com.app.superdistributor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SRComplaintActivity extends AppCompatActivity {

    EditText complaintTagET, complaintBodyET;
    Button submitComplaintBtn;
    String username;
    DatabaseReference database;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srcomplaint);

        database = FirebaseDatabase.getInstance().getReference();
        LoadingBar = new ProgressDialog(this);

        complaintTagET = findViewById(R.id.complaintTagET);
        complaintBodyET = findViewById(R.id.complaintBodyET);

        submitComplaintBtn = findViewById(R.id.submitComplaintBtn);

        username = getIntent().getStringExtra("SRUsername");
        
        submitComplaintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (complaintTagET.getText().toString().equals("")){
                    Toast.makeText(SRComplaintActivity.this, "Please add a tag for your complaint",Toast.LENGTH_SHORT).show();
                } else if (complaintBodyET.getText().toString().equals("")) {
                    Toast.makeText(SRComplaintActivity.this, "Please Describe your complaint",Toast.LENGTH_SHORT).show();
                }
                else {
                        HashMap<String,Object> complaint = new HashMap<>();
                        complaint.put(complaintTagET.getText().toString(),complaintBodyET.getText().toString());

                        database.child("SRs").child(username).child("Complaints")
                                .updateChildren(complaint).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        complaintTagET.setText("");
                                        complaintBodyET.setText("");
                                        Toast.makeText(SRComplaintActivity.this,"Your Complaint has been submitted",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SRComplaintActivity.this,"There was an error submitting your Complaint",Toast.LENGTH_SHORT).show();
                                    }
                                });

                }
            }
        });
    }
}